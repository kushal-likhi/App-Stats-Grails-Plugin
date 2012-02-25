package org.devunited.grails.plugin.appstats

import org.springframework.beans.BeanWrapper
import org.springframework.beans.PropertyAccessorFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class AppStatsController {
    def appStatsService

    def index = {
         render "hi"
    }

    def details = {
         render "bye"
    }
}
