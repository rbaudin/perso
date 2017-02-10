/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vlan;

/**
 *
 * @author rebaudin
 */
public class VlanConfiguration {
    public static void main(String[] args){
        int vlanDeb=2200;
        int vlanFin=2250;
        
        while(vlanDeb<=vlanFin){
            System.out.println("vlan "+vlanDeb);
            //System.out.println(". C:\\Users\\rebaudin\\Documents\\vmwarepowerCLI\\script\\pg_createSpecificVlanInCluster.ps1 ANG "+vlanDeb+" SEC-JOR-PRIV"+vlanDeb+" vSwitch2");
            vlanDeb++;
        }
    }
}
