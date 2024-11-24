package com.aenustar;

import java.util.Scanner;

interface State {
    void handle(Context context);
}

// 上下文类，负责保存当前状态
class Context {
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public void execute() {
        if (state != null) {
            state.handle(this);
        }
    }
}