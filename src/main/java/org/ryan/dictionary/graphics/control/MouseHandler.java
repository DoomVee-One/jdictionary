package org.ryan.dictionary.graphics.control;

import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.Application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

@Log
public class MouseHandler extends MouseAdapter {
    static final int SCROLL_SPEED = 20;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int direction = -e.getWheelRotation();
        int update = Application.yOffset + direction * SCROLL_SPEED;
        if(update > 0) Application.yOffset = update;
        Application.app.repaint();
    }
}
