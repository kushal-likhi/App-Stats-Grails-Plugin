package org.devunited.grails.plugin.appstats

class StringUtil {

  public static String replaceExtraSpacesWithSingleSpace(String str){
      return str.replaceAll("\t", "").replaceAll("\\s+", " ")
    }
}
