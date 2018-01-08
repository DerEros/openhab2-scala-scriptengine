package de.erna

import org.osgi.framework.{BundleActivator, BundleContext}

class ScalaEngineActivator extends BundleActivator {
  override def start( bundleContext: BundleContext ): Unit = {
    System.out.println("Starting bundle")
  }

  override def stop( bundleContext: BundleContext ): Unit = {
    System.out.println("Stopping bundle")
  }
}
