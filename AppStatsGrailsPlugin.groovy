class AppStatsGrailsPlugin {
    def version = "0.7"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = ['jquery': '1.7']
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Kushal Likhi & Divya Setia"
    def authorEmail = "kushal.likhi@gmail.com"
    def title = "Application Statistics Plugin"
    def description = '''\\
This Plugin provides advanced Statistics for your Grails Application
'''

    def documentation = "http://grails.org/plugin/app-stats"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
