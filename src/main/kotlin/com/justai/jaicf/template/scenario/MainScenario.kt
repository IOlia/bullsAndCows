package com.justai.jaicf.template.scenario

//import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario

val mainScenario = Scenario {
    state("start") {
        activators {
            regex("/start")
            regex("/старт")
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
}

