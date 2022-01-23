package it.units.db;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@WebListener
public class FirebaseBootstrapper implements ServletContextListener {

    private static final Logger log = Logger.getLogger(FirebaseBootstrapper.class.toString());

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        try {

            //String path = "/home/tibor/IdeaProjects/WebDevProject/src/main/webapp/WEB-INF/firebaseCredentials.json";
            //FileInputStream serviceAccount = new FileInputStream(path);
            InputStream serviceAccount = sce.getServletContext().getResourceAsStream("/WEB-INF/firebaseCredentials.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            log.info("Firebase is ready!");
        } catch (IOException e) {
            log.severe("Error during initialization of Firebase: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
