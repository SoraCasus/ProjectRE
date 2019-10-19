package com.soracasus.projectre.render.ui;

public class Colour {

	public float r;
	public float g;
	public float b;
	public float a;

	public Colour (float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0F;
	}

	public Colour (float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public float a () {
		return a;
	}

	public void a (float a) {
		this.a = a;
	}

	public float r () {
		return r;
	}

	public void r (float r) {
		this.r = r;
	}

	public float g () {
		return g;
	}

	public void g (float g) {
		this.g = g;
	}

	public float b () {
		return b;
	}

	public void b (float b) {
		this.b = b;
	}

	public byte[] getAsBytes() {
		int _r = (int)(this.r * 255);
		int _g = (int)(this.g * 255);
		int _b = (int)(this.b * 255);
		int _a = (int)(this.a * 255);

		return new byte[]{(byte)_r, (byte)_g, (byte)_b, (byte)_a};
	}
}
