package de.erna

import java.nio.file.{FileSystems, Paths, StandardWatchEventKinds, WatchEvent}
import java.util.concurrent.atomic.AtomicBoolean

import org.slf4s.Logging

import scala.language.implicitConversions
import collection.JavaConverters._

class DirectoryMonitor(val pathStr: String,
                       val onCreate: (WatchEvent[_]) => Unit,
                       val onModify: (WatchEvent[_]) => Unit,
                       val onDelete: (WatchEvent[_]) => Unit ) extends Logging {

  private val killPill = new AtomicBoolean(false)
  private val watchService = FileSystems.getDefault.newWatchService()

  init()

  private def init(): Unit = {
    val path = Paths.get(pathStr)

    val key = path.register(watchService,
                                     StandardWatchEventKinds.ENTRY_CREATE,
                                     StandardWatchEventKinds.ENTRY_MODIFY,
                                     StandardWatchEventKinds.ENTRY_DELETE )

    val monitorThread = createMonitorThread
    monitorThread.start()

    log.info("Started directory monitor")
  }

  private def createMonitorThread = {
    new Thread( ( ) => {
      log.info("Monitor thread started")
      while ( !killPill.get() ) {
        val watchKey = watchService.take()

        watchKey.pollEvents().asScala.foreach( handleWatchEvent )
      }
    } )
  }

  private def handleWatchEvent( event: WatchEvent[_]): Unit = {
    log.info(s"Got path change event on: ${event.context()}")
  }

  def stop(): Unit = {
    log.info("Stopping directory monitor")
    killPill.set(true)
  }

}
