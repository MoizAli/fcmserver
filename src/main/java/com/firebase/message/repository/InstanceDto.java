package com.firebase.message.repository;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Getter
public class InstanceDto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String appType;

    private String pushToken;
}
