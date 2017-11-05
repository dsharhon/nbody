// N-Body Simulator
// Dylan Sharhon
// March 18, 2015
// **********************************************************************************************************

// NOTE: Uses the StdDraw.java library by Robert Sedgewick and Kevin Wayne.

// An n-body simulation:
public class ParticleTest
{
	// Use to test the Particle object, etc.
	public static void main(String[] args)
	{
		// Size the window canvas. (Must be done first!)
		StdDraw.setCanvasSize(810, 600);
		StdDraw.setXscale(-33.75, 33.75);
		StdDraw.setYscale(-25, 25);

		// Make and populate an array of Particle objects.
		Particle[] particles = new Particle[3];
		particles[0] = new Particle( 1E04, new double[]{+0E0, +0E0}, new double[]{+0E0, +0E0});
		particles[1] = new Particle( 1E01, new double[]{+1E1, +0E1}, new double[]{+0E1, +3E1});
		particles[2] = new Particle( 1E01, new double[]{+2E1, -2E1}, new double[]{-1.5E1, +0E1});
		
		// Show initial information
		double[] momentum = Particle.calculateMomentum(particles);
		StdDraw.textLeft
		(	-33, 24, 
			"Version: " + 5
			+ "; Time Step Size: " + Particle.TIMESTEP_SIZE
			+ "; Initial Momentum (x, y): (" + momentum[0] + ", " + momentum[1] + ")"
		);
		
		// For each time step...
		for(int step = 0; step >= 0; step++)
		{
			// Clear canvas (optional).
			//StdDraw.show(20);
			//StdDraw.clear();
			
			// Draw each particle at its position.
			for(Particle i : particles)
			{
				StdDraw.setPenRadius(Math.sqrt(Math.sqrt(i.MASS))/1000);
				StdDraw.point(i.getPosition()[0], i.getPosition()[1]);
			}
			
			// Move the particles in memory.
			Particle.applyInverseSquareForce(1.0, particles);
			
			// Calculate the momentum of the system.
			momentum = Particle.calculateMomentum(particles);
			
			// Update statistics.
			StdDraw.setTitle
			(
				"Dylan's N-Body Simulator"
				+ "; Current Time Step: " + step
				+ "; Current Momentum (x,y): (" + (float)momentum[0] + ", " + (float)momentum[1] + ")"
			);
		}
	}

}
