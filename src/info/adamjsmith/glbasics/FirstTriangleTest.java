package info.adamjsmith.glbasics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import info.adamjsmith.framework.Game;
import info.adamjsmith.framework.Screen;
import info.adamjsmith.framework.impl.GLGame;
import info.adamjsmith.framework.impl.GLGraphics;

public class FirstTriangleTest extends GLGame {
	public Screen getStartScreen() {
		return new FirstTriangleScreen(this);
	}
	
	class FirstTriangleScreen extends Screen {
		final int VERTEX_SIZE = ( 2 + 2 ) * 4;
		GLGraphics glGraphics;
		FloatBuffer vertices;
		int textureId;
		
		public FirstTriangleScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			vertices = byteBuffer.asFloatBuffer();
			vertices.put ( new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 319.0f, 0.0f, 1.0f, 1.0f, 160.0f, 479.0f, 0.5f, 0.0f});
			vertices.flip();
			textureId = loadTexture("body.png");
		}
		
		public int loadTexture(String fileName) {
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(game.getFileIO().readAsset(fileName));
				GL10 gl = glGraphics.getGL();
				int textureIds[] = new int[1];
				gl.glGenTextures(1, textureIds, 0);
				int textureId = textureIds[0];
				gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
				bitmap.recycle();
				return textureId;
			} catch(IOException e) {
				Log.d("TexturedTriangleTest", "Couldn't load assets");
				throw new RuntimeException("Couldn't load asset");
			}
		}

		@Override
		public void update(float deltaTime) {
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
		}

		@Override
		public void present(float deltaTime) {
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			vertices.position(0);
			gl.glVertexPointer(2,  GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			vertices.position(2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
			
			//gl.glColor4f(1, 0, 0, 1);
			//gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			//gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
			//gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
	}
}
