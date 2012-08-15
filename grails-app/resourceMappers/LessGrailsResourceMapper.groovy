import org.grails.plugin.resource.mapper.MapperPhase
import org.springframework.core.io.Resource
import at.openfactory.lessgrails.LessProcessorService
/**
 * User: mkuhl
 * Date: 23.03.12
 * Time: 14:08
 */
class LessGrailsResourceMapper {
  def phase = MapperPhase.GENERATION

  LessProcessorService lessProcessorService;

  static defaultExcludes = [
      '**/*.png',
      '**/*.gif',
      '**/*.jpg',
      '**/*.jpeg',
      '**/*.gz',
      '**/*.zip'
  ]
  static defaultIncludes = [ '**/*.less' ]

  def map(resource, config) {
      if (!resource?.id)
        return

      if (resource.id.startsWith("bundle-"))  {
        log.debug("ignoring bundle: $resource.processedFile")
        return ;
      }

      File procDir = resource.processedFile.parentFile ;
      def outFile = new File(procDir, "${resource.processedFile.name}.css".toString())

      Resource res = resource.originalResource ;
      lessProcessorService.process(resource.processedFile, resource.originalUrl, outFile)

      log.debug("Css Output $outFile")
      log.debug "less compile ok."

      resource.processedFile = outFile ;
      resource.updateActualUrlFromProcessedFile()
      resource.contentType = 'text/css'
      resource.tagAttributes.rel = 'stylesheet'

  }

}
