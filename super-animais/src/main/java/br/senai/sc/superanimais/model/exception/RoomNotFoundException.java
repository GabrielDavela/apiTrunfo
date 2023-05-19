package br.senai.sc.superanimais.model.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomNotFoundException extends RuntimeException{

    private String message;

}
