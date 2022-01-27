package com.itfitness.mqttdemo.data

/**
 *
 * @Description:     java类作用描述
 * @Author:         作者名
 * @CreateDate:     2022/1/27 10:15
 * @UpdateUser:     更新者：
 * @UpdateDate:     2022/1/27 10:15
 * @UpdateRemark:   更新说明：
 */
enum class Topic{
    TOPIC_MSG{
        override fun value():String{
            return "testtopic/#"
        }
    },
    TOPIC_SEND{
        override fun value():String{
            return "testtopic/1"
        }
    };
    abstract fun value(): String
}