package bouncing_balls;

/**
 * The physics model.
 * 
 * This class is where you should implement your bouncing balls model.
 * 
 * The code has intentionally been kept as simple as possible, but if you wish, you can improve the design.
 * 
 * @author Simon Robillard
 *
 */
class Model {

	double areaWidth, areaHeight;
	
	Ball [] balls;

	Model(double width, double height) {
		areaWidth = width;
		areaHeight = height;
		
		// Initialize the model with a few balls
		balls = new Ball[2];
		balls[0] = new Ball(width / 3, height * 0.9, 1.2, 1.6, 0.2);
		balls[1] = new Ball(2 * width / 3, height * 0.7, 1.3, 1.0, 0.3);
	}

	void step(double deltaT) {
		// TODO this method implements one step of simulation with a step deltaT
		for (Ball b : balls) {
			// detect collision with the border
			if (b.x < b.radius || b.x > areaWidth - b.radius) {
				b.vx *= -1; // change direction of ball
			}
			if (b.y < b.radius || b.y > areaHeight - b.radius) {
				b.vy *= -1;
			}
			// compute new position according to the speed of the ball
			
			for (Ball c : balls){
				if ((b!=c)&&distance(b,c) <= (b.radius+c.radius)){
					impact(b,c);
				}
			}
			b.x += deltaT * b.vx;
			b.y += deltaT * b.vy;	
			
		}
	}

	void impact(Ball a, Ball b){
		double x = b.x-a.x;
		double y = b.y-a.y;
		double deg = Math.atan(y/x);
				
		double ahyp = a.vx*Math.cos(deg)-a.vy*Math.sin(deg);
		double ananhyp = a.vx*Math.sin(deg)+a.vy*Math.cos(deg);
		
		double bhyp = b.vx*Math.cos(deg)-b.vy*Math.sin(deg);
		double bnanhyp = b.vx*Math.sin(deg)+b.vy*Math.cos(deg);
		
		double tmp;
		double am = a.radius*a.radius;
		double bm = b.radius*b.radius;
		
		tmp = (bm*(bhyp-ahyp)+am*ahyp+bm*bhyp)/(am+bm);
		bhyp = (am*(ahyp-bhyp)+am*ahyp+bm*bhyp)/(am+bm);
		ahyp = tmp;

		double avec = Math.sqrt(ahyp*ahyp+ananhyp*ananhyp);
		double adeg = Math.atan(ananhyp/ahyp);
		double bvec = Math.sqrt(bhyp*bhyp+bnanhyp*bnanhyp);
		double bdeg = Math.atan(bnanhyp/bhyp);
		a.vx= avec*Math.cos(adeg);
		a.vy= avec*Math.sin(adeg);
		b.vx= bvec*Math.cos(bdeg);
		b.vy= bvec*Math.sin(bdeg);
	}


	double distance(Ball a, Ball b){
		double x = a.x-b.x;
		double y = a.y-b.y;
		double d = Math.sqrt(x*x+y*y);
		return d;
	}

	/**
	 * Simple inner class describing balls.
	 */
	class Ball {
		
		Ball(double x, double y, double vx, double vy, double r) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.radius = r;
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, radius;
	}
}
