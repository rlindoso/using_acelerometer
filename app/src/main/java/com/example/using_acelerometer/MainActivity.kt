package com.example.using_acelerometer


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var senSensorManager: SensorManager? = null
    private var senAccelerometer: Sensor? = null

    private var lastUpdate: Long = 0
    private var last_x: Float = 0.toFloat()
    private var last_y: Float = 0.toFloat()
    private var last_z: Float = 0.toFloat()
    private val SHAKE_THRESHOLD = 600

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val mySensor = sensorEvent?.sensor

        if (mySensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            val curTime = System.currentTimeMillis()

            if (curTime - lastUpdate > 100) {
                val diffTime = curTime - lastUpdate
                lastUpdate = curTime

                val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000

                if (speed > SHAKE_THRESHOLD) {
                    getRandomNumber()
                }

                last_x = x
                last_y = y
                last_z = z
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        senSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        senAccelerometer = senSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        senSensorManager!!.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        senSensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        senSensorManager!!.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun getRandomNumber() {
        val numbersGenerated = ArrayList<Int>()

        var i = 0
        while (i < 6) {
            val randNumber = Random()
            val iNumber = randNumber.nextInt(48) + 1

            if (!numbersGenerated.contains(iNumber)) {
                numbersGenerated.add(iNumber)
            } else {
                i--
            }
            i++
        }
        number_1.text = numbersGenerated[0].toString()
        number_2.text = numbersGenerated[1].toString()
        number_3.text = numbersGenerated[2].toString()
        number_4.text = numbersGenerated[3].toString()
        number_5.text = numbersGenerated[4].toString()
        number_6.text = numbersGenerated[5].toString()

        ball_1.visibility = View.INVISIBLE
        ball_2.visibility = View.INVISIBLE
        ball_3.visibility = View.INVISIBLE
        ball_4.visibility = View.INVISIBLE
        ball_5.visibility = View.INVISIBLE
        ball_6.visibility = View.INVISIBLE

        val a = AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom)
        ball_6.visibility = View.VISIBLE
        ball_6.clearAnimation()
        ball_6.startAnimation(a)

        ball_5.visibility = View.VISIBLE
        ball_5.clearAnimation()
        ball_5.startAnimation(a)

        ball_4.visibility = View.VISIBLE
        ball_4.clearAnimation()
        ball_4.startAnimation(a)

        ball_3.visibility = View.VISIBLE
        ball_3.clearAnimation()
        ball_3.startAnimation(a)

        ball_2.visibility = View.VISIBLE
        ball_2.clearAnimation()
        ball_2.startAnimation(a)

        ball_1.visibility = View.VISIBLE
        ball_1.clearAnimation()
        ball_1.startAnimation(a)
    }
}
