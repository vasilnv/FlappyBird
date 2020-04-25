package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener{

	public static FlappyBird flappyBird;
	public final int WIDTH = 800, HEIGHT = 800;
	public Renderer renderer;
	public Rectangle bird;
	public ArrayList<Rectangle> columns;
	public int ticks, yMotion, score;
	public boolean gameOver, started;
	public Random rand;
	
	
	
	public FlappyBird() {
		
		
		JFrame jFrame = new JFrame();
		Timer timer = new Timer(20, this);
		renderer = new Renderer();
		rand = new Random();
		
		jFrame.add(renderer);
		jFrame.setTitle("Flappy Bird");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(WIDTH, HEIGHT);
		jFrame.addMouseListener(this);
		
		
		jFrame.setResizable(false);
		jFrame.setVisible(true);
	
		bird = new Rectangle(WIDTH/2-10, HEIGHT/2-10, 20, 20);
		columns = new ArrayList<Rectangle>();
		
		addNewColumn(true);
		addNewColumn(true);
		addNewColumn(true);
		addNewColumn(true);
		addNewColumn(true);
		
		
		timer.start();
		
		}
	
	public void addNewColumn(boolean start){
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		
		if (start) {
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		
		else {
			columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, width, HEIGHT - height - space));
		}
			
	
	}
	

	public void paintColumn(Graphics g, Rectangle r) {
		
		g.setColor(Color.green.darker());
		g.fillRect(r.x, r.y, r.width, r.height);
		
	}
	
	
	public void jump() {
		if (gameOver) {
			bird = new Rectangle(WIDTH/2-10, HEIGHT/2-10, 20, 20);
			columns.clear();
			
			addNewColumn(true);
			addNewColumn(true);
			addNewColumn(true);
			addNewColumn(true);
			addNewColumn(true);
			
			yMotion = 0;
			score = 0;
			
			
			
			gameOver=false;
			
		}
		if (!started) {
			started = true;
		}
		
		else if (!gameOver){
			if(yMotion>0) {
				yMotion=0;
			}
			yMotion-=10;
		}
	}
	

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		int speed = 6;
		
		ticks ++;
		if(started) {

			for (int i=0; i < columns.size(); i++) {
					Rectangle column = columns.get(i);
					column.x-=speed;
			
				}
		
				if (ticks % 2 == 0 && yMotion < 15) {
					yMotion += 2;
				}
	
				for (int i=0; i < columns.size(); i++) {
					
					Rectangle column = columns.get(i);
	
					if (column.width + column.x < 0) {
						columns.remove(column);
						if (column.y == 0) {	
							addNewColumn(false);
						}
					}
			
				}
		
				bird.y += yMotion;
				
				for(Rectangle column:columns) {
					
					if(column.y==0 && bird.x + bird.width/2 > column.x + column.width/2 - 3 && bird.x + bird.width/2 < column.x + column.width/2 + 3 )
					{
						score ++;
					}
					if(column.intersects(bird))
					{
						gameOver = true;
						bird.x = column.x - bird.width;
					
					}		
				}
		
				if(bird.y + yMotion >= HEIGHT - 120) {
					bird.y = HEIGHT - 120 - bird.height;
				}
				
				if(bird.y > HEIGHT - 150 || bird.y < 0) {
				gameOver = true;
				
				}
			
				
			}
		
		renderer.repaint();
			
		
	}
	
	
	public void repaint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-120, WIDTH, 120);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for(Rectangle column:columns) {
			paintColumn(g, column);
		}	
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		
		if(!started) {
			g.drawString("Click to start", 100, HEIGHT/2 - 50);	
		}
		
		if(gameOver) {
		g.drawString("Game Over!", 100, HEIGHT/2 - 50);	
		}
		
		if(!gameOver && started) {
		g.drawString(String.valueOf(score), WIDTH/2 - 25, 100);	
		}		
		
	}

	
	public static void main(String[] args) {
		
		flappyBird = new FlappyBird();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
 

