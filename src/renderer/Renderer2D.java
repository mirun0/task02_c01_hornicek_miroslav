package renderer;

import java.awt.Color;

import controller.handler.modeHandler.Mode;
import rasterize.Raster;
import renderer.ui.UIRenderer;
import world.Scene2D;

public class Renderer2D {

    private final Raster raster;

    private final SceneRenderer sceneRenderer;
    private final UIRenderer uiRenderer;

    public Renderer2D(Raster raster, Scene2D scene) {
        this.raster = raster;

        this.sceneRenderer = new SceneRenderer(raster);
        this.uiRenderer = new UIRenderer(raster.getGraphics());
    }

    public void render(Scene2D scene, Mode mode, int width, int height) {
        raster.clear(Color.BLACK);

        sceneRenderer.renderScene(scene);

        renderUI(mode, width, height);
    }

    public void renderUI(Mode mode, int width, int height) {
        uiRenderer.renderUI(mode, width, height);
    }
}
