/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book;

import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;

/**
 *
 * @author Dr. PANDA 002
 */
public class SessionUtil {

    // --------------------- File Sessions Handler ---------------------
    public static SessionHandler fileSessionHandler() {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
//        sessionCache.setSessionDataStore(fileSessionDataStore());
//        sessionHandler.setSessionCache(sessionCache);
//        sessionHandler.setHttpOnly(true);
        // make additional changes to your SessionHandler here
        return sessionHandler;
    }

//    private static FileSessionDataStore fileSessionDataStore() {
//        FileSessionDataStore fileSessionDataStore = new FileSessionDataStore();
//        File baseDir = new File(System.getProperty("java.io.tmpdir"));
//        File storeDir = new File(baseDir, "javalin-session-store");
//        storeDir.mkdir();
//        fileSessionDataStore.setStoreDir(storeDir);
//        return fileSessionDataStore;
//    }
}
