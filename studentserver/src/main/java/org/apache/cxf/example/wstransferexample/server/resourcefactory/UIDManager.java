/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.cxf.example.wstransferexample.server.resourcefactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Erich Duda
 */
public class UIDManager {
    
    private static final AtomicInteger uid = new AtomicInteger(1);
    
    public static int getUID() {
        return uid.getAndIncrement();
    }
    
}
