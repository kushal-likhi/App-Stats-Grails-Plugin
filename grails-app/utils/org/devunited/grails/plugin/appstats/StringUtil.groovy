package org.devunited.grails.plugin.appstats

class StringUtil {

    public static String replaceExtraSpacesWithSingleSpace(String str) {
        return str.replaceAll("\t", "").replaceAll("\\s+", " ")
    }

    public static String toLowerCaseFirstCharacter(String name) {
        if (!name) { return '' }
        List<String> strings = name.split(" ")
        List<String> stringsWithTitleCase = []
        strings.each {string ->
            string = string.substring(0, 1).toLowerCase() + string.substring(1)
            stringsWithTitleCase.add(string)
        }
        return stringsWithTitleCase.join(" ")
    }
}
