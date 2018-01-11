package de.erna

import javax.script.{Bindings, ScriptContext}

import org.slf4s.Logging

import scala.tools.nsc.interpreter.Scripted

object Main extends App with Logging {
  def say(msg: String): Unit = {
    System.out.println(msg)
  }

  System.out.println("Hello App")

//  ammonite.Main().runCode("println(\"Hello Ammonite\")")

  val scripted = Scripted()
  val context = scripted.compileContext
  val bindings = context.getBindings(ScriptContext.ENGINE_SCOPE)

  bindings.put("say", say _)
  bindings.put("msg", "mymsg")

  val compiledScript = scripted.compile("throw new Exception(msg.asInstanceOf[String])", context)

  compiledScript.eval()
}
