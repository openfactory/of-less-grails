package at.openfactory.lessgrails

import at.openfactory.lessgrails.LessImportsPreProcessor
/**
 * Created with IntelliJ IDEA.
 * User: mkuhl
 * Date: 25.03.12
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
class TestLessImportsPreProcessor extends GroovyTestCase {
  LessImportsPreProcessor lip = new LessImportsPreProcessor() ;

  @Override
  protected void setUp() {
//    lip.lessDirs << new File("C:\\proj\\softmachine\\grails2\\plugins\\of-less-grails\\test\\unit\\at\\openfactory\\lessgrails\\data\\less")
    lip.lessDirs << new File("C:\\proj\\softmachine\\grails2\\plugins\\of-less-grails\\test\\unit\\at\\openfactory\\lessgrails\\data\\otherless")
  }

  void testNoImport() {
    String sIn = "// test less preproc"
    String result = lip.process(sIn) ;
    assertEquals(sIn, result) ;
  }

  void testMinInput() {
    String sIn = "@import 'test1.less';"
    String result = lip.process(sIn, "C:\\proj\\softmachine\\grails2\\plugins\\of-less-grails\\test\\unit\\at\\openfactory\\lessgrails\\data\\less") ;
    println (result)

  }
}
