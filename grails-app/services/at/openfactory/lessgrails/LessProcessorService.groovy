package at.openfactory.lessgrails

import org.springframework.beans.factory.InitializingBean

import org.codehaus.groovy.grails.plugins.GrailsPlugin
import com.asual.lesscss.LessEngine

/**
 * implements the less compiler as service
 */
class LessProcessorService implements InitializingBean {
  static transactional = false

  def grailsApplication
  def applicationContext = org.codehaus.groovy.grails.commons.ApplicationHolder.application.mainContext
  LessImportsPreProcessor lipp = new LessImportsPreProcessor()
//  MyLessCompiler lessCompiler = new MyLessCompiler();
  LessEngine     lessEngine

  void afterPropertiesSet() {
    def pmgr = applicationContext.pluginManager
    GrailsPlugin lessPlugin = pmgr.getGrailsPlugin('of-less-grails')
    def pp = pmgr.getPluginPath("/")

    applicationContext.pluginManager.userPlugins.each {plugin->
      def path = plugin.pluginPath
      def pres = applicationContext.getResource("$plugin.pluginPath/less") ;
      if (pres && pres.exists()) {
        log.info("adding plugin "+ plugin + "less resources: $pres.description")
        lipp.lessDirs << pres.file
      }
    }
    def lessDir = grailsApplication.parentContext.getResource("less")
    if (lessDir && lessDir.exists())
      lipp.lessDirs << lessDir.file

    log.debug("start initializing JS Environment")
    lessEngine = new LessEngine()
//    lessCompiler.init() ;
    log.debug("done initializing JS Environment")
  }

  def process(File fileIn, String origUrl, File fileOut) {
    log.debug("Less processing $fileIn")

    def origRes = applicationContext.getResource(origUrl) ;
    def curDir = "."
    if (origRes && origRes.exists())
      curDir = origRes.file.parent

    String out = lipp.process(fileIn, curDir)
    File ppFile = new File(fileIn.parentFile, "${fileIn.name}.pp") ;
    ppFile.write(out)
    log.debug "Preprocessed: $ppFile"

    try {
//      lessCompiler.compile(ppFile, fileOut)
      lessEngine.compile(ppFile, fileOut)
    }
    catch (Exception ex) {
      log.error "failed to compile less: $fileIn"
      log.error "reason: $ex"
      throw ex ;
    }
  }
}
