package com.itfitness.mqttandroid

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.itfitness.mqttandroid.data.Qos
import com.itfitness.mqttandroid.data.Topic
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val server = "tcp://mqtt.p2hp.com:1883" //服务端地址
        val mqttHelper = MQTTHelper(this,server,"123","123")
        mqttHelper.connect(Topic.TOPIC_MSG, Qos.QOS_TWO,false,object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {

            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                //收到消息
                message?.payload?.let { ToastUtils.showShort(String(it)) }
                LogUtils.eTag("消息", message?.payload?.let { String(it) })
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {



            }
        })
        val etMsg = findViewById<EditText>(R.id.et_msg)
        findViewById<Button>(R.id.tv_send).setOnClickListener {
            //发送消息
            mqttHelper.publish(Topic.TOPIC_SEND,etMsg.text.toString(),Qos.QOS_TWO)
        }
    }
}