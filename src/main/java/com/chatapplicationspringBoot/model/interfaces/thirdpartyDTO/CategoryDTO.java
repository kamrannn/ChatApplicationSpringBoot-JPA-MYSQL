package com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO;

import lombok.Data;

import java.io.Serializable;
@Data
public class CategoryDTO implements Serializable {
    private Long id; //category id that will come from 3rd party API
    private String name; //category name that will come from 3rd party API
}
