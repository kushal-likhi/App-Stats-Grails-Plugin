package org.devunited.grails.plugin.appstats
class IPInfo {
    String internalIPAddress
    String externalIPAddress
    String hostName

    IPInfo() {
        InetAddress address = InetAddress.getLocalHost();
        this.internalIPAddress = address.getHostAddress();
        this.hostName = address.hostName;
        this.externalIPAddress = WANIpAddress()
    }

    String WANIpAddress() {
        URL externalIP = new URL("http://automation.whatismyip.com/n09230945.asp");
        BufferedReader bf = new BufferedReader(new InputStreamReader(externalIP.openStream()));
        String ipAddress = bf.readLine();
        return ipAddress
    }


}
