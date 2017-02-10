/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cisco.eman;

import java.io.BufferedReader;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rebaudin The input file should be 10.48.72.13:pommepoire
 */
public class main {

    public static void main(String[] args) {
        String outfileName = "output.txt";
        String FILENAME = "input.txt";
        ArrayList listentries = new ArrayList<DnsEntry>();
        String choix = "";
        Scanner scan = new Scanner(System.in);
        String path= System.getProperty("user.dir") + System.getProperty("file.separator");
        
        System.out.println("Welcome to DNS Entry Management");

        while (!choix.equals("quit")) {

            System.out.println("Que voulez vous faire ?");
            System.out.println("- delete");
            System.out.println("- update(add/update)");
            System.out.println("- quit");
            System.out.println("- help");
            System.out.println("Choix:");
            
            choix = scan.next();
            switch (choix) {
                case "update":
                    System.out.println("Welcome to Update menu");
                    try {
                        //READ THE FILE
                        FileReader fr = new FileReader(FILENAME);
                        BufferedReader br = new BufferedReader(fr);

                        String sCurrentLine;

                        //LOAD THE ENTRIES IN THE ARRYLIST 
                        System.out.println("Loading information in memory...");
                        while ((sCurrentLine = br.readLine()) != null) {
                            DnsEntry thednsentry = new DnsEntry(new Ip(sCurrentLine.split(":")[1]), new Dns(sCurrentLine.split(":")[0]));
                            listentries.add(thednsentry);
                        }
                        DnsEntryManamagent dem = new DnsEntryManamagent();

                        //OUTPUT FILE
                        FileWriter fileWriter = new FileWriter(outfileName);
                        // Always wrap FileWriter in BufferedWriter.
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        dem.UpdateEntry(listentries, bufferedWriter);
                        System.out.println("File Generated:"+path+"outfileName");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "delete":
                    System.out.println("Welcome to Delete menu");
                    try {
                        //READ THE FILE
                        FileReader fr = new FileReader(FILENAME);
                        BufferedReader br = new BufferedReader(fr);

                        String sCurrentLine;

                        //LOAD THE ENTRIES IN THE ARRYLIST 
                        System.out.println("Loading information in memory...");
                        while ((sCurrentLine = br.readLine()) != null) {
                            DnsEntry thednsentry = new DnsEntry(sCurrentLine);
                            listentries.add(thednsentry);
                        }
                        DnsEntryManamagent dem = new DnsEntryManamagent();

                        //OUTPUT FILE
                        FileWriter fileWriter = new FileWriter(outfileName);
                        // Always wrap FileWriter in BufferedWriter.
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        dem.DeleteEntry(listentries, bufferedWriter);
                        System.out.println("File Generated:"+path+"outfileName");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                    case "help":
                        System.out.println("Put the file in that folder: File Generated:"+path);
                        System.out.println("To Update : 10.48.68.161:cdn-mcs-11");
                        System.out.println("To Delete IP : 10.48.68.161");
                        System.out.println("To Delete DNS : cdn-mcs-11");
                        System.out.println("Choose the right option in the menu and the file will be generated");
                        break;
            }
             System.out.println("-----");
        }

    }
}
