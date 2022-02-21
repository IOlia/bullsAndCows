package com.justai.jaicf.template.scenario


//import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import java.util.Random
import com.justai.jaicf.hook.AnyErrorHook
//import com.justai.jaicf.reactions.buttons
//import com.justai.jaicf.reactions.toState

val mainScenario = Scenario {
    state("hello") {
        activators {
            intent("hello")
        }
        action {
            reactions.say("Привет!")
        }
    }
}

