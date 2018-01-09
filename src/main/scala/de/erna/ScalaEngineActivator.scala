package de.erna

import org.osgi.framework.{BundleActivator, BundleContext}
import org.slf4s.Logging

class ScalaEngineActivator extends BundleActivator with Logging {
  override def start( bundleContext: BundleContext ): Unit = {
    log.info("Starting bundle")
  }

  override def stop( bundleContext: BundleContext ): Unit = {
    log.info("Stopping bundle")
  }
}
