package com.firebase.message.service;

import com.firebase.message.configuration.FirebaseConfig;
import com.firebase.message.model.MessageSubmission;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Firebase {

    private final FirebaseConfig.FirebaseMessagingWrapper firebaseMessaging;

    public void sendMessage(MessageSubmission messageSubmission) {
        com.google.firebase.messaging.Message.Builder msg = com.google.firebase.messaging.Message.builder()
                .setToken(messageSubmission.getInstanceDto().getPushToken());

        AndroidNotification androidNotification = AndroidNotification.builder()
                .setTitle(messageSubmission.getTitle())
                .setBody(messageSubmission.getMessage())
                .build();

        AndroidConfig.Builder androidConfig = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(androidNotification);

        if(messageSubmission.getData() != null) {
            androidConfig.putAllData(messageSubmission.getData());
        }

        AndroidConfig build = androidConfig.build();
        msg = msg.setAndroidConfig(build);

        addFutureCallbacks(firebaseMessaging.sendAsync(msg.build()), messageSubmission);
    }

    public void addFutureCallbacks(ApiFuture<String> future, final MessageSubmission messageSubmission) {
        ApiFutures.addCallback(future, new ApiFutureCallback<String>() {

            @Override
            public void onSuccess(String result) {
                log.info("Sent message: " + messageSubmission.getTitle() + " " + messageSubmission.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
//                if (throwable instanceof FirebaseMessagingException) {
//                    handleFirebaseMessagingException((FirebaseMessagingException) throwable, messageSubmission.getUserDataId());
//                } else if (throwable.getCause() != null && throwable.getCause() instanceof FirebaseMessagingException) {
//                    handleFirebaseMessagingException((FirebaseMessagingException) throwable.getCause(), messageSubmission.getUserDataId());
//                }
//                withBookingIdAndProvider(messageSubmission.getBookingId(), messageSubmission.getProvider(),
//                        () -> log.error("Failed to send message: " + messageSubmission.getTitle() + " " + messageSubmission.getBody(), throwable));
                log.error("Failed to send message: " + messageSubmission.getTitle() + " " + messageSubmission.getMessage(), throwable);

            }
        });
    }

}