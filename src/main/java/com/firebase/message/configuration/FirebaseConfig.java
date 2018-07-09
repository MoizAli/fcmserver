package com.firebase.message.configuration;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;

@Slf4j
@Configuration
@Profile({"!bdd"})
public class FirebaseConfig {

    @Bean
    @SneakyThrows
    public FirebaseMessagingWrapper firebaseMessaging() {
//        String configPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
//        if (configPath == null) {
//            log.warn("Environment variable not defined: GOOGLE_APPLICATION_CREDENTIALS");
//        }
        //TODO: Add your serviceAccountKey before running the app
        FileInputStream serviceAccount =
                new FileInputStream("gcloud/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                //TODO : Add your Database Url. Looks like "https://xxxx.firebaseio.com"
//                .setDatabaseUrl()
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance(firebaseApp);
        return (message) -> firebaseMessaging.sendAsync(message);
    }

    public interface FirebaseMessagingWrapper {
        ApiFuture<String> sendAsync(@NonNull Message message);
    }
}