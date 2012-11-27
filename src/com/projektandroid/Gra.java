package com.projektandroid;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.projektandroid.Gra;
import com.screens.LevelScreen;
import com.screens.SplashScreen;



public class Gra  extends Game implements ApplicationListener{
	
	Mesh sphere,plane;
	Texture steel,wood;
	PerspectiveCamera camera;
	Sprite[][] sprites = new Sprite[10][10];
	Matrix4 matrix = new Matrix4();
	SpriteBatch batch;
	private static float spherex = 0.0f, spherey = 0.0f, spherez = 0.0f;
	private static Vector3 move;
	public static final String LOG = Gra.class.getSimpleName();
	public static final boolean DEV_MODE = false;
	public void create() {
		//Loading meshes + textures
		try{
			
			System.out.println("ASD dsad sakjdDNSAOKDNSAFJ DSAD");
			
			InputStream spherefile = Gdx.files.internal("sphere.obj").read();
			sphere = ObjLoader.loadObj(spherefile,false);
			spherefile.close();
			
			InputStream planefile = Gdx.files.internal("plane.obj").read();
			plane = ObjLoader.loadObj(planefile,false);
			planefile.close();
			FileHandle steelfile1 = Gdx.files.internal("skin/uiskin.json");
			FileHandle steelfile = Gdx.files.internal("Steel.png");
			steel = new Texture(steelfile);
			
			FileHandle woodfile = Gdx.files.internal("Wood.png");
			wood = new Texture(woodfile);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//Camera set
		float aspectRatio =	(float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new PerspectiveCamera(67,2f * aspectRatio, 2f);
		camera.near = 1;
		camera.far = 100;
		camera.view.set(new Matrix4());
		camera.projection.set(new Matrix4());
		camera.position.set(1,10,3);
		camera.lookAt(-1, -1, -1);
		
		
		move = new Vector3();
		
	}

	 @Override
	    public void resize(
	        int width,
	        int height )
	    {
	        super.resize( width, height );
	        Gdx.app.log( Gra.LOG, "Resizing game to: " + width + " x " + height );

	        // show the splash screen when the game is resized for the first time;
	        // this approach avoids calling the screen's resize method repeatedly
	        if( getScreen() == null ) {
	            if( DEV_MODE ) {
	                setScreen( new LevelScreen( this, 0 ) );
	            } else {
	                setScreen( new SplashScreen( this ) );
	            }
	        }
	    }
	public void render() {
		GL10 gl = Gdx.graphics.getGL10();
		camera.update();
		camera.apply(Gdx.gl10);
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		
		
		
		//Gdx.gl.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		
		steel.bind();
		gl.glPushMatrix();
		sphereMovement(gl);
		sphere.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		
		wood.bind();
		gl.glPushMatrix();
		gl.glRotatef(45,0,1,0);
		plane.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		
	      /*if ( Gdx.input.isTouched())
	      {
	         camera.rotate(new Vector3(0.0f,1.0f,0.0f),0.2f);
	    	 camera.translate(0.1f,0.2f,0.2f);
	      }*/
	}

	public void pause() {	
	}

	public void resume() {
	}

	public void dispose() {
		sphere.dispose();
		steel.dispose();
		plane.dispose();
		wood.dispose();
	}
	
	public void sphereMovement(GL10 gl){
		spherex = Gdx.input.getAccelerometerX();
		spherey = Gdx.input.getAccelerometerY();
		spherez = Gdx.input.getAccelerometerZ();
		
		gl.glTranslatef(spherex/2, spherey/2, 0);
		//gl.glRotatef(45 * (-Gdx.input.getAccelerometerY() / 5), 0, 1, 0);
	}
    public void setScreen(Screen screen)
        {
            super.setScreen( screen );
            Gdx.app.log( Gra.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
        }
	
	
}