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
enum class Qos{
    QOS_ZERO{
        override fun value():Int{
            return 0
        }
    },
    QOS_ONE{
        override fun value():Int{
            return 1
        }
    },
    QOS_TWO{
        override fun value():Int{
            return 2
        }
    };
    abstract fun value(): Int
}