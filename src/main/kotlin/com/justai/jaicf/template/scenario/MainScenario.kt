package com.justai.jaicf.template.scenario

//import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import java.util.Random

val random = Random()
fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
}

fun formIntendedNumber() : String{
    val array = ArrayList<Int>()
    var randomNumber: Int
    while (array.size < 4) {
        randomNumber = rand(0, 10)
        if (!array.contains(randomNumber)) {
            array.add(randomNumber)
        }
    }
    return array.joinToString("")
}

val mainScenario = Scenario {
    state("start") {
        activators {
            regex(".start")
            regex(".старт.")
        }
        action {
            reactions.say("Я бот Василий. Умею играть в Быки и коровы. Сыграем?")
        }


        state("letsPlay") {
            activators {
                intent("yes")
            }
            action {
                reactions.say("Напомню правила: я загадываю 4-значное число с неповторяющимися цифрами. Твоя задача - угадать его. В ответ я скажу, сколько быков (сколько цифр ты угадал вплоть до позиции) и сколько коров (цифр без совпадения позиций). Например: я загадал число 3219, ты пробуешь отгадать и называешь 2310. Это будет один бык (цифра 1 угадана с позицией) и две коровы (цифры 2 и 3 угаданы без совпадения позиций). Если тебе надоест играть, просто скажи Стоп. Итак, я загадал число. Попробуй отгадать?!")
                val intendedNumber = formIntendedNumber()
                reactions.say(intendedNumber)
            }

        }

        state("dontWantToPlay") {
            activators {
                intent("no")
            }
            action {
                reactions.say("Хорошо, может быть, в другой раз.")
            }
        }
    }

    state("hello") {
        activators {
            intent("hello")
        }
        action {
            reactions.say("Приветик")
        }
    }

    fallback {
        reactions.say("Прости, я пока понимаю не все запросы.")
    }
}

