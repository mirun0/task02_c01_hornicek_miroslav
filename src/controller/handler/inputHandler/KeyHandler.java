package controller.handler.inputHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import controller.handler.modeHandler.Action;
import controller.handler.modeHandler.Mode;

public class KeyHandler extends KeyAdapter {

    private ArrayList<Integer> validModeKeys; // klavesy co meni ruzne mody (Mode)
    private ArrayList<Integer> validNormalKeys; // klavesy co delaji veci, ktere neprepnou mod (Action)

    private Mode activeMode;
    private boolean modChanged;

    public KeyHandler() {
        this.validModeKeys = new ArrayList<>();
        this.validNormalKeys = new ArrayList<>();
        this.activeMode = Mode.POINT_MOVING;
        this.modChanged = false;

        validModeKeys.addAll(Arrays.asList(KeyEvent.VK_B, KeyEvent.VK_ESCAPE, KeyEvent.VK_V, KeyEvent.VK_N, KeyEvent.VK_D, KeyEvent.VK_R, KeyEvent.VK_Q));
        validNormalKeys.addAll(Arrays.asList(KeyEvent.VK_SHIFT, KeyEvent.VK_C, KeyEvent.VK_G, KeyEvent.VK_F));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        if(validNormalKeys.contains(e.getKeyCode())) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SHIFT: {
                    Action.SNAP.setOn();
                    break;
                }
                case KeyEvent.VK_G: {
                    Action.GRADIENT.toggle();
                    break;
                }
                case KeyEvent.VK_C: {
                    Action.CLEAR.setOn();
                    break;
                }
                case KeyEvent.VK_F: {
                    Action.FILLING.toggle();
                    break;
                }
            }

            return;
        }

        if(validModeKeys.contains(e.getKeyCode())) {
            modChanged = true;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_V: {
                    activeMode = Mode.LINE_CREATION;
                    break;
                }
                case KeyEvent.VK_B: {
                    activeMode = Mode.POLYGON_CREATION;
                    break;
                }
                case KeyEvent.VK_ESCAPE: {
                    activeMode = Mode.POINT_MOVING;
                    break;
                }
                case KeyEvent.VK_N: {
                    activeMode = Mode.POINT_CREATION;
                    break;
                }
                case KeyEvent.VK_D: {
                    activeMode = Mode.POINT_DELETION;
                    break;
                }
                case KeyEvent.VK_R: {
                    activeMode = Mode.RECTAGLE_CREATION;
                    break;
                }
                case KeyEvent.VK_Q: {
                    activeMode = Mode.POLYGON_CLIPPING;
                    break;
                }
                
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Action.SNAP.setOff();
        }
    }

    public Mode getActiveMode() {
        return activeMode;
    }

    public boolean isModChanged() {
        return modChanged;
    }

    public void setModChanged(boolean modChanged) {
        this.modChanged = modChanged;
    }
}
