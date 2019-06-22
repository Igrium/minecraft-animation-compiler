package org.metaversemedia.mcanim.util;

/**
 * Vector class for ease of use throughout program
 * @author Igrium
 *
 */
public class Vector {
	private float X;
	private float Y;
	private float Z;
	
	public Vector(float x, float y, float z) {
		X = x;
		Y = y;
		Z = z;
	}
	
	public Vector(double x, double y, double z) {
		X = (float) x;
		Y = (float) y;
		Z = (float) z;
	}
	
	public float X() {
		return X;
	}
	
	public float Y() {
		return Y;
	}
	
	public float Z() {
		return Z;
	}
	
	/**
	 * Returns the length of the vector via the Pythagorean Theorem
	 * @return length
	 */
	public float length() {
		return (float) Math.sqrt(X*X + Y*Y + Z*Z);
	}
	
	/**
	 * Returns if this vector equals the passed vector
	 * @param Vector to compare
	 * @return
	 */
	public boolean equals(Vector vec) {
		return (X == vec.X() && Y == vec.Y() && Z == vec.Z());
	}
	
	/**
	 * Returns this vector in the format <X,Y,Z>
	 */
	public String toString() {
		return "<"+X+","+Y+","+Z+">";
		
	}
	
	/**
	 * Rotate vector around origin by given radians
	 * @param xRat
	 * @param yRat
	 * @param zRat
	 * @return Rotated vector
	 */
	public Vector rotateRadians(double xRat, double yRat, double zRat) {
		// Rotate around X axis
		double x1 = X;
		double y1 = Y*Math.cos(xRat) - Z*Math.sin(xRat);
		double z1 = Y*Math.sin(xRat) + Z*Math.cos(xRat);
		
		// Rotate around Y axis
		double x2 = x1*Math.cos(yRat) + z1*Math.sin(yRat);
		double y2 = y1;
		double z2 = -1*x1*Math.sin(yRat) + z1*Math.cos(yRat);
		
		// Rotate around Z axis
		double x3 = x2*Math.cos(zRat) - y2*Math.sin(zRat);
		double y3 = x2*Math.sin(zRat) + y2*Math.cos(zRat);
		double z3 = z2;
		
		return new Vector(x3,y3,z3);
	}
	
	/**
	 * Rotate vector around origin by given degrees
	 * @param xDeg
	 * @param yDeg
	 * @param zDeg
	 * @return Rotated vector
	 */
	public Vector rotateDegrees(double xDeg, double yDeg, double zDeg) {
		return rotateRadians(Math.toRadians(xDeg), Math.toRadians(yDeg), Math.toRadians(zDeg));
	}
	
	/**
	 * Adds vector 1 and vector 2
	 * @param vec1
	 * @param vec2
	 * @return sum
	 */
	public static Vector add(Vector vec1, Vector vec2) {
		return new Vector(vec1.X + vec2.X, vec1.Y + vec2.Y, vec1.Z + vec2.Z);
	}
	
	/**
	 * Multiplies vector by value
	 * @param vec
	 * @param val
	 * @return product
	 */
	public static Vector multiply(Vector vec, float val) {
		return new Vector(vec.X*val, vec.Y*val, vec.Z*val);
	}
	
	/**
	 * Subtracts vector 2 from vector 1
	 * @param vec1
	 * @param vec2
	 * @return difference
	 */
	public static Vector subtract(Vector vec1, Vector vec2) {
		return new Vector(vec1.X - vec2.X, vec1.Y - vec2.Y, vec1.Z - vec2.Z);
	}
	
	/**
	 * Divides vector by value
	 * @param vec
	 * @param val
	 * @return quotient
	 */
	public static Vector divide(Vector vec, float val) {
		return multiply(vec, 1/val);
	}
	
}
