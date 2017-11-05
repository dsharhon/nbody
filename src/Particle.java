
public class Particle
{
	// Class constants.
	public static final double TIMESTEP_SIZE= 0.001;
	
	// Known physical properties used for calculations:
	public final double MASS;		// constant mass	m
	protected double[] position;	// known position	r(t)
	protected double[] velocity;	// known velocity	v(t - dt/2)
	
	// Constructor ********************************************************************************
	public Particle(double mass, double[] position, double[] velocity)
	{
		// Construct a valid particle.
		if(mass > 0)
		{
			this.MASS = mass;
			this.position = position;
			this.velocity = velocity;
		}
		else
		{
			throw new RuntimeException("Must have positive mass.");
		}
	}
	
	// Static methods *****************************************************************************
	
	// Accelerate a pair of particles due to their mutual attraction.
	public static void applyInverseSquareForce(double forceConstant, Particle i, Particle j)
	{
		// Calculate the displacement from i to j.
		double[] displacementVector = new double[2];
		displacementVector[0] = j.position[0] - i.position[0];
		displacementVector[1] = j.position[1] - i.position[1];
		double displacementMagnitude = Math.hypot(displacementVector[0], displacementVector[1]);
		
		// Calculate the acceleration factor from i to j.
		double[] accelerationFactor = new double[2];
		accelerationFactor[0] = forceConstant * displacementVector[0] / Math.pow(displacementMagnitude, 3);
		accelerationFactor[1] = forceConstant * displacementVector[1] / Math.pow(displacementMagnitude, 3);
		
		// Update the particles' velocities used for the next position calculations.
		i.velocity[0] += accelerationFactor[0] * j.MASS * TIMESTEP_SIZE;
		i.velocity[1] += accelerationFactor[1] * j.MASS * TIMESTEP_SIZE;
		j.velocity[0] -= accelerationFactor[0] * i.MASS * TIMESTEP_SIZE;
		j.velocity[1] -= accelerationFactor[1] * i.MASS * TIMESTEP_SIZE;
	}
	
	// Accelerate a collection of particles and store their positions for the next time step.
	public static void applyInverseSquareForce(double forceConstant, Particle[] particles)
	{
		// For each pair...
		for(int i = 0; i < particles.length - 1; i++)
		{
			for(int j = i + 1; j < particles.length; j++)
			{
				// Update the pairs' velocities.
				applyInverseSquareForce(forceConstant, particles[i], particles[j]);
			}
		}
		
		// Update the particles' positions once all force calculations are done.
		for(Particle i : particles)
		{
			i.position[0] += i.velocity[0] * TIMESTEP_SIZE;
			i.position[1] += i.velocity[1] * TIMESTEP_SIZE;
		}
	}
	
	// Calculate the total momentum of the system.
	public static double[] calculateMomentum(Particle[] particles)
	{
		double[] momentum = new double[]{0,0};
		for(Particle i : particles)
		{
			momentum[0] += i.MASS * i.velocity[0];
			momentum[1] += i.MASS * i.velocity[1];
		}
		return momentum;
	}
	
	// Getters and Setters **********************************************************************************
	
	// Get position vector.
	public double[] getPosition()
	{
		return this.position;
	}
	
	// Get velocity vector.
	public double[] getVelocity()
	{
		return this.velocity;
	}
}
