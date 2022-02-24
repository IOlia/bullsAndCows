package com.justai.jaicf.template.scenario


//import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import java.util.Random
import com.justai.jaicf.hook.AnyErrorHook
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
//import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.BotContext
//import com.justai.jaicf.context.ExecutionContext

val random = Random()
var intendedNumber = ""
fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
}

fun formIntendedNumber(): String {
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

fun checkLetterRepetition(number: String): Boolean {
    val array: List<String> = number.map { it.toString() }
    var otherArray = ArrayList<String>()

    for (x in array.indices) {
        if (!otherArray.contains(array[x])) {
            otherArray.add(array[x])
        } else {
            return true
        }
    }
    return false
}

fun conform(word: String, number: Number): String {
    if (word == "бык") {
        return when (number) {
            0 -> "быков"
            1 -> "бык"
            else -> "быка"
        }
    }

    if (word == "корова") {
        return when (number) {
            0 -> "коров"
            1 -> "корова"
            else -> "коровы"
        }
    }
    return ""
}

fun checkBullsAndCows(intendedNumber: String, clientNumber: String): String {

    var bulls = 0
    var cows = 0

    for (x in 0..3) {
        if (intendedNumber.contains(clientNumber[x])) {
            if (clientNumber[x] == intendedNumber[x]) {
                bulls ++
            } else {
                cows ++
            }
        }
    }
    //val answer = "В твоем числе: $bulls" + conform("бык", bulls) + "и $cows" + conform("корова", cows) + "."

    return "В твоем числе: $bulls ${conform("бык", bulls)} и $cows ${conform("корова", cows)}."

}

val mainScenario = Scenario {
    handle<AnyErrorHook> {
        logger.error("", exception)
        reactions.say("Извините, технические неполадки! Попробуйте повторить Ваш запрос.")
    }

    state("hello") {
        activators {
            intent("hello")
        }
        action {
            reactions.say("Привет!")
        }
    }

    state("start") {
        activators {
            regex("((/)?(start|старт))")
        }
        action {
            reactions.say("Я бот Василий. Умею играть в Быки и коровы. Сыграем?")
        }

            state("letsPlay") {
                activators {
                    intent("yes")
                }
                action {
                    reactions.say("Напомню правила: я загадываю 4-значное число с неповторяющимися цифрами. Твоя задача - угадать его. В ответ я скажу, сколько в твоем числе быков (сколько цифр ты угадал вплоть до позиции) и сколько коров (цифр без совпадения позиций). Например: я загадал число 3219, ты пробуешь отгадать и называешь 2310. Это будет один бык (цифра 1 угадана с позицией) и две коровы (цифры 2 и 3 угаданы без совпадения позиций). Если тебе надоест играть, просто скажи Стоп. Итак, я загадал число. Попробуй отгадать?!")
                    //attempts = 0
                    //context.session["intendedNumber"] = formIntendedNumber()
                    intendedNumber = formIntendedNumber()
                    //reactions.say("${context.session["intendedNumber"]}")
                    reactions.say(intendedNumber)
                }

                state("getNumber") {
                    activators {
                        regex("\\d+")
                    }
                    action {

                        //if (attempts == 0) {
                        //    attempts = 1
                        //} else {
                        //    attempts += 1
                        //}
                        //var x = request
                        var number : String = request.input
                        if (number.length < 4) {
                            reactions.say("В твоем числе меньше четырех цифр. Попробуй еще раз!")
                        } else if (number.length > 4) {
                            reactions.say("В твоем числе больше четырех цифр. Давай еще раз!")
                        } else if (checkLetterRepetition(number)) {
                            reactions.say("В твоем числе не должно быть повторяющихся чисел, попробуй другое число!")
                        } else if (number == intendedNumber) {
                            reactions.say("Ура, ты отгадал!")
                        //    reactions.say("Попытки: $attempts")
                            reactions.buttons("Сыграть еще раз" to "/start/letsPlay", "Хватит играть" to "/start/dontWantToPlay")
                        } else {
                            var answer: String = checkBullsAndCows(intendedNumber, number)
                            reactions.say(answer)
                        }
                    }
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

            fallback {
                reactions.say("Прости, я пока понимаю не все запросы. Вы сказали: ${request.input}")
            }
    }

}

