package bouncing_balls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import bouncing_balls.Model.Ball;

@SuppressWarnings("serial")
public final class Animator extends JPanel implements ActionListener {

	private static final double pixelsPerMeter = 200;
	private Model model;
	private Timer timer;
	private double deltaT;
    private BufferedImage bImage;
    private double lastTick;

	public Animator(int pixelWidth, int pixelHeight, int fps) {
		super(true);

        this.bImage = new BufferedImage(pixelWidth, pixelHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bImage.getWidth(), bImage.getHeight());

		this.timer = new Timer(200 / fps, this);
		//this.timer = new Timer(33, this);
		this.deltaT = 0.2 / fps;
		this.model = new Model(pixelWidth / pixelsPerMeter, pixelHeight / pixelsPerMeter);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(pixelWidth, pixelHeight));
	}


	public void start() {
        lastTick = System.nanoTime();
		timer.start();
	}

    public void stop() {
    	timer.stop();
    }

    public void drawBalls(Color c) {
        Graphics2D g = (Graphics2D) bImage.getGraphics();
        g.setColor(c);
		for (Ball b : model.balls) {
			g.setColor(c);
			double x = b.x - b.radius;
			double y = b.y + b.radius;
            double ix = x * pixelsPerMeter;
            double iy = bImage.getHeight() - (y * pixelsPerMeter);
            double size = b.radius * 2 * pixelsPerMeter;
			Ellipse2D.Double e = new Ellipse2D.Double(ix, iy, size, size);
			g.fill(e);
            this.repaint(new Rectangle((int) ix-1, (int) iy-1, (int) size+2, (int) size+2));
        }
        g.dispose();
    }

	@Override
	protected void paintComponent(Graphics g) {
        g.drawImage(bImage, 0, 0, null);
	}
	
    @Override
    public void actionPerformed(ActionEvent e) {
        double tick = System.nanoTime();

        drawBalls(Color.WHITE);

        Graphics2D g = (Graphics2D) bImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), 50);
        g.dispose();
        this.repaint(new Rectangle(0, 0, this.getWidth(), 50));

    	model.step(deltaT);
        drawBalls(Color.RED);

        lastTick = tick;
    }
	
	public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Animator anim = new Animator(800, 600, 120);
                JFrame frame = new JFrame("Bouncing balls");
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.add(anim);
            	frame.pack();
            	frame.setLocationRelativeTo(null);
            	frame.setVisible(true);
            	anim.start();
            }
        });
    }
}