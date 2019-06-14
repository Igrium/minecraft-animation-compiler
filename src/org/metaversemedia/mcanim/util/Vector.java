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
