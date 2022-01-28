package com.itfitness.mqttandroid

import android.content.Context
import android.text.TextUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.LogUtils
import com.itfitness.mqttandroid.data.Qos
import com.itfitness.mqttandroid.data.Topic
import com.itfitness.mqttlibrary.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*


/**
 *
 * @Description:     java类作用描述
 * @Author:         作者名
 * @CreateDate:     2022/1/26 16:59
 * @UpdateUser:     更新者：
 * @UpdateDate:     2022/1/26 16:59
 * @UpdateRemark:   更新说明：
 */
class MQTTHelper{
    private val mqttClient: MqttAndroidClient
    private val connectOptions: MqttConnectOptions
    private var mqttActionListener: IMqttActionListener? = null
    constructor(context: Context, serverUrl:String, name:String, pass:String){
        val androidId = DeviceUtils.getAndroidID()
        val clientId = if(!TextUtils.isEmpty(androidId)){
            androidId
        }else{
            UUID.randomUUID().toString()
        }
        mqttClient = MqttAndroidClient(context,serverUrl,clientId)
        connectOptions = MqttConnectOptions().apply {
            isCleanSession = false //是否会话持久化
            connectionTimeout = 30 //连接超时时间
            keepAliveInterval = 10 //发送心跳时间
            userName = name //如果设置了认证，填的用户名
            password = pass.toCharArray() //用户密码
        }
    }

    /**
     * 连接
     * @param mqttCallback 接到订阅的消息的回调
     * @param isFailRetry 失败是否重新连接
     */
    fun connect(topic: Topic, qos: Qos, isFailRetry:Boolean, mqttCallback: MqttCallback){
        mqttClient.setCallback(mqttCallback)
        if(mqttActionListener == null){
            mqttActionListener = object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    LogUtils.eTag("连接","连接成功")
                    subscribe(topic,qos)
                }
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    //失败重连
                    LogUtils.eTag("连接","连接失败重试${exception?.message}")
                    if (isFailRetry){
                        mqttClient.connect(connectOptions,null,mqttActionListener)
                    }
                }
            }
        }
        mqttClient.connect(connectOptions,null,mqttActionListener)
    }

    /**
     * 订阅
     */
    private fun subscribe(topic: Topic,qos:Qos){
        mqttClient.subscribe(topic.value(),qos.value())
    }

    /**
     * 发布
     */
    fun publish(topic:Topic,message:String,qos:Qos){
        val msg = MqttMessage()
        msg.isRetained = false
        msg.payload = message.toByteArray()
        msg.qos = qos.value()
        mqttClient.publish(topic.value(),msg)
    }

    /**
     * 断开连接
     */
    fun disconnect(){
        mqttClient.disconnect()
    }
}