package jvm.doing.com.proxy

class Person : Human, Clever {

    override fun eat(food: Food): String {
        return "${food.name} 真好吃！"
    }
}