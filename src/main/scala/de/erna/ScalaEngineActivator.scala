package de.erna

import javax.script.ScriptContext

import de.erna.Main.say
import org.osgi.framework.{BundleActivator, BundleContext}
import org.slf4s.Logging

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.Scripted

class ScalaEngineActivator extends BundleActivator with Logging {
  var directoryMonitor: DirectoryMonitor = _

  override def start( bundleContext: BundleContext ): Unit = {
    log.info("Starting bundle")

    directoryMonitor = new DirectoryMonitor("/tmp", () => _, () => _, () => _)

    val settings = new Settings()
    settings.classpath.value = System.getProperty("java.class.path")
    settings.usejavacp.value = true

    val scripted = Scripted(settings = settings)
    val context = scripted.compileContext
    val bindings = context.getBindings(ScriptContext.ENGINE_SCOPE)

    bindings.put("say", say _)
    bindings.put("msg", "mymsg")

    val compiledScript = scripted.compile("throw new Exception(msg.asInstanceOf[String])", context)

    compiledScript.eval()


  }

  override def stop( bundleContext: BundleContext ): Unit = {
    log.info("Stopping bundle")
    directoryMonitor.stop()
  }
}
