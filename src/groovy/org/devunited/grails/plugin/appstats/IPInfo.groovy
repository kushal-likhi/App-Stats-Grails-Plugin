package org.devunited.grails.plugin.appstats
class IPInfo {
    String internalIPAddress
    String hostName

    IPInfo() {
        InetAddress address = InetAddress.getLocalHost();
        this.internalIPAddress = address.getHostAddress();
        this.hostName = address.hostName;
    }
}
