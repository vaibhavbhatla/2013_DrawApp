package drawapp;

public class Turtle
{
	private double posX;
	private double posY;
	private double orientation;
	
	public Turtle()
	{
		this.posX = 0;
		this.posY = 0;
		this.orientation = 0;
	}
	
	public double getX()
	{
		return posX;
	}
	
	public double getY()
	{
		return posY;
	}
	
	public double getOrientation()
	{
		return orientation;
	}
	
	public void setX(double posX)
	{
		this.posX = posX;
	}
	
	public void setY(double posY)
	{
		this.posY = posY;
	}
	
	public void turnLeft(double angle)
	{
		this.orientation -= angle;
	}
	
	public void turnRight(double angle)
	{
		this.orientation += angle;
	}
	
	public void moveForward(double distance)
	{
		this.posX += (distance * Math.cos(orientation));
		this.posY += (distance * Math.sin(orientation));
	}
}
