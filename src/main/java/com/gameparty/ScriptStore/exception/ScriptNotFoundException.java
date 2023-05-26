package com.gameparty.ScriptStore.exception;

public class ScriptNotFoundException extends Exception {
    public ScriptNotFoundException() {
        super("Статья не найден");
    }
}
