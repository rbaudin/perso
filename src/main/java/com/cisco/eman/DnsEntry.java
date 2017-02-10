/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cisco.eman;

import java.util.regex.Pattern;

/**
 *
 * @author rebaudin
 */
public class DnsEntry {
    private Ip ip;
    private Dns dns;
    public static final String IPV4_REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";
    public boolean isIpTodelete;
    
    public DnsEntry(Ip _ip, Dns _dns) {
        ip=_ip;
        dns=_dns;
    }
    
    public DnsEntry(String _info) {
        Pattern isAnIP = Pattern.compile(IPV4_REGEX);
        
        //_INFO IS AN IP
        if(isAnIP.matcher(_info).matches())
        {
            //System.out.println("It's an IP !");
            ip=new Ip(_info);
            isIpTodelete = true;
        }
        else
        {
            //System.out.println("It's a DNS entry !");
            dns= new Dns(_info);
            isIpTodelete = false;
        }
    }
    
    
    public Dns getDns() {
        return dns;
    }
    
    public Ip getIp() {
        return ip;
    }
    
    
}
