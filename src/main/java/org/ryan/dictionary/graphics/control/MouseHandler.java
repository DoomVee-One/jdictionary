package org.ryan.dictionary.graphics.control;

import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.Application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

@Log
public class MouseHandler extends MouseAdapter {
    static final int SCROLL_SPEED = 20;
    static final int TOP_MARGIN = 20;
    static final int LEFT_MARGIN = 20;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int direction = e.getWheelRotation();

        if(!e.isShiftDown()) {
            int update = Application.yOffset + direction * SCROLL_SPEED;
            if(update > -TOP_MARGIN) Application.yOffset = update;
        } else {
            int update = Application.xOffset + direction * SCROLL_SPEED;
            if(update > -LEFT_MARGIN) Application.xOffset = update;
        }
        Application.app.repaint();
    }
}
