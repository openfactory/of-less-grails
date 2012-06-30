package at.openfactory.lessgrails

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: mkuhl
 * Date: 25.03.12
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class LessFragment {
  private static Logger log = Logger.getLogger(LessFragment)
  private def fragmentResolver ;
  private def name ;
  private LessFragment parent ;
  private StringBuilder content ;
  String currentPath ;

  LessFragment(String name, LessFragment parent, Closure fragmentResolver, String sIn, String currentPath=".") {
    this.name = name ;
    this.fragmentResolver = fragmentResolver ;
    this.parent = parent
    this.content = new StringBuilder(sIn) ;
    this.currentPath = currentPath ;
  }


  protected String resolve () {
    StringBuilder result = new StringBuilder();
    content.eachLine {line-> processLine(line, result) }

    return result.toString()
  }

  protected processLine(String line, StringBuilder out) {
    if (line.startsWith("@import ")) {
      //todo: this should be really done with an regexp !
      def end = line.indexOf(";")
      def target = line["@import ".length()..end-1].trim() ;
      target = target.replaceAll("\"","")
      target = target.replaceAll("\'","")
      target = target.replaceAll(";","")

      out << "// start resolving import: $target\n"

      LessFragment imported = fragmentResolver.call (this, target)
      if (imported == null)
        throw new RuntimeException("$name: could not resolve import $target ín $name ")

      out << imported.resolve()
      out << "\n// end resolved import: $target \n"
    }
    else {
      out << line
      out << "\n"
    }
  }

}
