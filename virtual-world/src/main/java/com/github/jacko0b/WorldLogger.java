package com.github.jacko0b;

import java.util.ArrayList;
import java.util.List;

public class WorldLogger {

    private final List<String> messages = new ArrayList<>();

    public void log(String msg) {
        messages.add(msg);
    }

    public void clear() {
        messages.clear();
    }

    public List<String> getMessages() {
        return messages;
    }
}
