package com.firebase.message.model;

import com.firebase.message.repository.InstanceDto;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by moizali on 6/30/18.
 */
@Data
public class MessageSubmission {
    public final String title;
    public final String message;
    public final HashMap<String, String> data;
    public final InstanceDto instanceDto;
}
