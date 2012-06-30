package at.openfactory.lessgrails

/**
 * User: mkuhl
 * Date: 23.03.12
 * Time: 16:11
 *
 * pre-processing the import statements for a .less resource since this seems the only sane way to handle it.
 */
class LessImportsPreProcessor {
  List<File> lessDirs = new ArrayList<File>()

  public LessImportsPreProcessor() {
  }


  public String process(String sin, currentDir=".") {
    def lfm = new LessFragment("testroot", (LessFragment)null, resolveImport, sin, currentDir as String)
    return lfm.resolve() ;
  }

  public String process(File fileIn, currentDir=".")  {
    def lfm = new LessFragment(fileIn as String, (LessFragment)null, resolveImport, fileIn.text, currentDir as String)
    return lfm.resolve() ;
  }

  def resolveImport = {LessFragment parent, String name->
    log.info "==>attempt to resolve import $name for $parent.name"

    LessFragment imported = null ;

    // try current path first
    File file = new File("$parent.currentPath/$name")
    if (file.canRead()) {
      return  new LessFragment(file.absolutePath, parent, resolveImport, file.text, file.parent) ;
    }

    lessDirs.each {dir->
      file = new File(dir, name) ;
      if (file.canRead()) {
        imported = new LessFragment(file.absolutePath, parent, resolveImport, file.text, file.parent) ;
        return
      }
    }

    return imported
  }


}
