/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cisco.eman;

import java.io.*;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author rebaudin
 */
public class DnsEntryManamagent {

    private String dnsinfo, ipinfo;

    public DnsEntryManamagent() {
        dnsinfo = new String();
        ipinfo = new String();
    }

    private String removeEntry(DnsEntry _dnsEntry) {
        return "-f=id -i=" + _dnsEntry.getIp().getIp() + " -n=" + _dnsEntry.getDns().getDns();
    }

    private String removeEntry(Dns _dns, String _ip) {
        return "-f=id -i=" + _ip + " -n=" + _dns.getDns();
    }

    private String removeEntry(Ip _ip, String _dns) {
        return "-f=id -i=" + _ip.getIp() + " -n=" + _dns;
    }

    private String removeEntry(String _ip, String _dns) {
        return "-f=id -i=" + _ip + " -n=" + _dns;
    }

    private String addEntry(DnsEntry _dnsEntry) {
        return "-f=ia -Contact1=bru-calo-admin -Contact1type=\"Mail Alias\" -i=" + _dnsEntry.getIp().getIp() + " -n=" + _dnsEntry.getDns().getDns();
    }

    /**
     * PRE: POST:
     */
    public void UpdateEntry(ArrayList<DnsEntry> _listentries, BufferedWriter _bufferedWriter) {

        try {
            //FOR EACH LINE IN THE INPUT FILE
            for (DnsEntry oneentry : _listentries) {

                //IF THE PROVIDED DNS NAME IS ALREADY USED IN THE DNS WE HAVE TO DELETE IT FIRST
                if (!performNSLookup(oneentry.getDns()).equals("") && !ipinfo.equals(oneentry.getIp().getIp())) {
                    System.out.println("ici");
                    System.out.println(ipinfo+" "+performNSLookup(oneentry.getDns()));
                    System.out.println(oneentry.getIp().getIp());
                    _bufferedWriter.write(removeEntry(oneentry.getDns(), ipinfo));
                    _bufferedWriter.newLine();
                }

                //IF THE PROVIDED IP IS ALREADY USED WE HAVE TO DELETE IT
                if (!performNSLookup(oneentry.getIp()).equals("") && !dnsinfo.contains(oneentry.getDns().getDns())) {
                    System.out.println("la");
                    System.out.println(dnsinfo);
                    System.out.println(oneentry.getDns().getDns());
                    _bufferedWriter.write(removeEntry(oneentry.getIp(), dnsinfo));
                    _bufferedWriter.newLine();
                }

                //WE DON T WAN TO ADD IF THE ENTRY ALREADY EXIST IN THE DNS DATABSE!
                if (!ipinfo.equals(oneentry.getIp().getIp()) && !dnsinfo.contains(oneentry.getDns().getDns())) {
                    _bufferedWriter.write(addEntry(oneentry));
                    _bufferedWriter.newLine();
                }

                System.out.println("--");
                dnsinfo = "";
                ipinfo = "";
            }
            _bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file '"
                    + "output.txt" + "'");
        }
    }

    /**
     * PRE: POST:
     */
    public void DeleteEntry(ArrayList<DnsEntry> _listentries, BufferedWriter _bufferedWriter) {
        try {
            //FOR EACH LINE IN THE INPUT FILE
            for (DnsEntry oneentry : _listentries) {

                //IF THE PROVIDED DNS NAME IS ALREADY USED IN THE DNS WE HAVE TO DELETE IT FIRST
                if (oneentry.isIpTodelete) {
                    performNSLookup(oneentry.getIp());
                    if (!dnsinfo.equals("")) {
                        _bufferedWriter.write(removeEntry(oneentry.getIp(), dnsinfo));
                        _bufferedWriter.newLine();
                    }
                } else {
                    performNSLookup(oneentry.getDns());
                    if (!ipinfo.equals("")) {
                        _bufferedWriter.write(removeEntry(oneentry.getDns(), ipinfo));
                        _bufferedWriter.newLine();
                    }
                }

                //System.out.println("--");
                dnsinfo = "";
                ipinfo = "";
            }
            _bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file '"
                    + "output.txt" + "'");
        }

    }

    /**
     * PRE: POST:
     */
    public boolean infoAlreadyExistInDNs(DnsEntry _dnsEntry) {
        //SET THE DNSINFO AND IPINFO IF SOMETHING ALREADY EXIST WITH THE IP AND PROVIDED
        this.performNSLookup(_dnsEntry.getIp());
        this.performNSLookup(_dnsEntry.getDns());

        if (dnsinfo.equals("") || ipinfo.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * PRE: POST:
     */
    public boolean dnsExist(Ip _ip) {
        try {
            InetAddress inetHost = InetAddress.getByName(_ip.getIp());
            return true;
        } catch (UnknownHostException ex) {
            //System.out.println("Unrecognized host");
            return false;
        }
    }

    /**
     * PRE: POST:
     */
    public boolean dnsExist(Dns _dns) {
        try {
            InetAddress inetHost = InetAddress.getByName(_dns.getDns());
            return true;
        } catch (UnknownHostException ex) {
            //System.out.println("Unrecognized host");
            return false;
        }
    }

    /**
     * PRE: POST:
     */
    public String performNSLookup(Dns _dns) {

        try {
            //MUST CREATE AN OBJECT FOR THE GETHOSTNAME()
            //InetAddress inetHost = InetAddress.getByName(_dns);
            //String hostName = inetHost.getHostName();
            //System.out.println("The host name was: " + inetHost.getHostName());
            //System.out.println("The hosts IP address is: " + inetHost.getByName("rebaudin-ubuntu"));

            InetAddress inetHost = InetAddress.getByName(_dns.getDns());
            //String hostName = inetHost.getHostName();
            //System.out.println("The host name was: " + hostName);
            //System.out.println("The hosts IP address is: " + inetHost.getHostAddress());
            ipinfo = inetHost.getHostAddress();
            return ipinfo;
        } catch (UnknownHostException ex) {

            //System.out.println("Unrecognized host");
        }
        return "";
    }

    /**
     * PRE: POST:
     */
    public String performNSLookup(Ip _ip) {

        try {

            InetAddress inetHost = InetAddress.getByName(_ip.getIp());
            //System.out.println("The host name was: " + hostName);
            //System.out.println("The hosts IP address is: " + inetHost.getHostAddress());
            dnsinfo = inetHost.getHostName();
            return dnsinfo;
        } catch (UnknownHostException ex) {

            //System.out.println("Unrecognized host");
        }
        return "";
    }

}
