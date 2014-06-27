package info.adamjsmith.glbasics;

import javax.microedition.khronos.opengles.GL10;

import info.adamjsmith.framework.Game;
import info.adamjsmith.framework.Screen;
import info.adamjsmith.framework.gl.FPSCounter;
import info.adamjsmith.framework.gl.Texture;
import info.adamjsmith.framework.gl.Vertices;
import info.adamjsmith.framework.impl.GLGame;
import info.adamjsmith.framework.impl.GLGraphics;

public class BodyTest extends GLGame {
	public Screen getStartScreen() {
		return new BodyScreen(this);
	}
	
	class BodyScreen extends Screen {
		static final int NUM_BODS = 100;
		GLGraphics glGraphics;
		Texture bodyTexture;
		Vertices bodyModel;
		Body[] bodies;
		FPSCounter fpsCounter = new FPSCounter();
		
		public BodyScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			bodyTexture = new Texture((GLGame)game, "body.png");
			
			bodyModel = new Vertices(glGraphics, 4, 12, false, true);
			bodyModel.setVertices(new float[] { -16, -16, 0, 1,
												 16, -16, 1, 1,
												 16, 16, 1, 0,
												 -16, 16, 0, 0,}, 0, 16);
			bodyModel.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
			
			bodies = new Body[100];
			for(int i = 0; i < 100; i++) {
				bodies[i] = new Body();
			}
		}

		@Override
		public void update(float deltaTime) {
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			for(int i = 0; i < NUM_BODS; i++) {
				bodies[i].update(deltaTime);
			}
			
		}

		@Override
		public void present(float deltaTime) {
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			
			fpsCounter.logFrame();
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			for(int i = 0; i < NUM_BODS; i++) {
				gl.glLoadIdentity();
				gl.glTranslatef(bodies[i].x, bodies[i].y, 0);
				bodyModel.draw(GL10.GL_TRIANGLES, 0, 6);
			}
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0,  320,  0, 480, 1, -1);
			bodyTexture.reload();
			gl.glEnable(GL10.GL_TEXTURE_2D);
			bodyTexture.bind();			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
	}
}
