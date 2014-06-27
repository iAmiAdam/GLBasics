package info.adamjsmith.glbasics;

import info.adamjsmith.framework.GameObject;

public class Cannon extends GameObject {
	public float angle;
	
	public Cannon(float x, float y, float width, float height) {
		super(x, y, width, height);
		angle = 0;
	}
}
