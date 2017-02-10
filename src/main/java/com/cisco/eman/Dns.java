/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cisco.eman;

/**
 *
 * @author rebaudin
 */
public class Dns {
    
    String dns;
    
    public Dns() {
        
    }
    
    public Dns(String _dns) {
        dns=_dns;
    }
    
    public String getDns(){
        return dns;
    }
}
