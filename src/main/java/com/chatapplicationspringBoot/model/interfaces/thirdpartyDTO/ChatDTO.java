package com.chatapplicationspringBoot.model.interfaces.thirdpartyDTO;

import lombok.Data;
import java.io.Serializable;

/**
 * The type Chat dto.
 */
@Data
public class ChatDTO implements Serializable {
    private long chatId; //chat id that will come from 3rd party API
    private String question; //Question that will come from 3rd party API
    private String answer; //Answer that will come from 3rd party API
    private String questionDate; //Question date that will come from 3rd party API
    private String answerDate; //Answer date that will come from 3rd party API
}
