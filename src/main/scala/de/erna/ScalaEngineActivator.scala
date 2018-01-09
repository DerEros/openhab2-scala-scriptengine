package de.erna

import org.osgi.framework.{BundleActivator, BundleContext}
import org.slf4s.Logging

class ScalaEngineActivator extends BundleActivator with Logging {
  var directoryMonitor: DirectoryMonitor = _

  override def start( bundleContext: BundleContext ): Unit = {
    log.info("Starting bundle")

    directoryMonitor = new DirectoryMonitor("/tmp", () => _, () => _, () => _)

  }

  override def stop( bundleContext: BundleContext ): Unit = {
    log.info("Stopping bundle")
    directoryMonitor.stop()
  }
}
