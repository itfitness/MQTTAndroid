package com.itfitness.mqttandroid

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.itfitness.mqttdemo.MQTTHelper
import com.itfitness.mqttdemo.data.Qos
import com.itfitness.mqttdemo.data.Topic
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val server1 = "tcp://mqtt.p2hp.com:1883"
        val server2 = "mqtt://broker.emqx.io:1883"
        val mqttHelper = MQTTHelper(this,server1,"123","123")
        mqttHelper.connect(Topic.TOPIC_MSG, Qos.QOS_TWO,false,object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {

            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                message?.payload?.let { ToastUtils.showShort(String(it)) }
                LogUtils.eTag("消息", message?.payload?.let { String(it) })
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {



            }
        })
        findViewById<TextView>(R.id.tv_send).setOnClickListener {
            mqttHelper.publish(Topic.TOPIC_SEND,"你哈哈哈哈的空间按时",Qos.QOS_TWO)
        }
    }
}