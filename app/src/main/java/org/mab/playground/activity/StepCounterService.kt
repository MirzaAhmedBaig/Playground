package org.mab.playground.activity

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import android.util.Log


/**
 * Created by Mirza Ahmed Baig on 12/12/18.
 * Avantari Technologies
 * mirza@avantari.org
 */
class StepCounterService : Service() {

    private var isRegistered = false
    private val TAG = StepCounterService::class.java.simpleName
    private var stepsCounterListener: StepCounterListener? = null

    private var mSteps = 0
    private var mCounterSteps = 0
    private var mPreviousCounterSteps = 0
    private val BATCH_LATENCY_5s = 5000000

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isSensorSupported()) {
            if (!isRegistered)
                registerStepCounterEventListener()
        }
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isRegistered) {
            unregisterListeners()
        }
    }

    fun setStepsListener(stepsCounterListener: StepCounterListener) {
        this.stepsCounterListener = stepsCounterListener
    }


    private fun isSensorSupported() =
            (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                    && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
                    && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR))


    private fun registerStepCounterEventListener() {
        Log.d(TAG, "Call to registerStepCounterEventListener")
        val sensorManager = getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        isRegistered = sensorManager.registerListener(mListener, sensor, SensorManager.SENSOR_DELAY_NORMAL, BATCH_LATENCY_5s)
    }

    private fun unregisterListeners() {
        val sensorManager = getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(mListener)
        Log.i(TAG, "Sensor listener unregistered.")
    }


    private val mListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                Log.d(TAG, "Total steps : ${event.values[0].toInt()} Time : ${event.timestamp}")
                if (mCounterSteps < 1) {
                    mCounterSteps = event.values[0].toInt()
                }
                mSteps = event.values[0].toInt() - mCounterSteps
                mSteps += mPreviousCounterSteps
                stepsCounterListener?.onStepsReceived(mSteps)
                Log.i(TAG, "New step detected by STEP_COUNTER sensor. Total step count: $mSteps")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }
    }

    private val mBinder = StepServiceBinder()

    inner class StepServiceBinder : Binder() {
        val stepsServiceInstance: StepCounterService
            get() = this@StepCounterService
    }
}