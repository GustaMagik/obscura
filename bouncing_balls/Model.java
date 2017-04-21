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
		double deg = Math.atan2(y,x);
		
		double aV = Math.sqrt(a.vx*a.vx+a.vy*a.vy);
		double ad = Math.atan2(a.vy,a.vx);

		double bV = Math.sqrt(b.vx*b.vx+b.vy*b.vy);
		double bd = Math.atan2(b.vy,b.vx);


		//double ahyp = a.vx*Math.cos(deg)-a.vy*Math.sin(deg);
		//double ananhyp = a.vx*Math.sin(deg)+a.vy*Math.cos(deg);
		
		//double bhyp = b.vx*Math.cos(deg)-b.vy*Math.sin(deg);
		//double bnanhyp = b.vx*Math.sin(deg)+b.vy*Math.cos(deg);
		double ahyp = aV*Math.cos(ad-deg);
		double ananhyp = aV*Math.sin(ad-deg);
		double bhyp = bV*Math.cos(bd-deg);
		double bnanhyp = bV*Math.sin(bd-deg);

		double tmp;
		double am = a.radius*a.radius;
		double bm = b.radius*b.radius;
		
		tmp = (bm*(bhyp-ahyp)+am*ahyp+bm*bhyp)/(am+bm);
		bhyp = (am*(ahyp-bhyp)+am*ahyp+bm*bhyp)/(am+bm);
		ahyp = tmp;

		double avec = Math.sqrt(ahyp*ahyp+ananhyp*ananhyp);
		double adeg = Math.atan2(ananhyp,ahyp);
		double bvec = Math.sqrt(bhyp*bhyp+bnanhyp*bnanhyp);
		double bdeg = Math.atan2(bnanhyp,bhyp);
		a.vx= avec*Math.cos(adeg+deg);
		a.vy= avec*Math.sin(adeg+deg);
		b.vx= bvec*Math.cos(bdeg+deg);
		b.vy= bvec*Math.sin(bdeg+deg);
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
