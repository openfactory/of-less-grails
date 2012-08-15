modules = {
    'of-less-demo' {
      resource id:'js', url:[plugin: 'of-less-grails', dir:'js/less-grails', file:"application.js"], nominify: true
      resource id:'less', url:[plugin: 'of-less-grails', dir:'less/less-grails', file:"application.less"], nominify: true, attrs:[type:'css']
    }

    'less-compiler' {
      resource id:'less-compiler', url:[plugin: 'of-less-grails', dir:'js/less-grails', file:"less-1.3.0.min.js"],
              nominify: true, disposition: 'head'
    }

}