
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Minesweeper extends JFrame implements MouseListener {

	private int tile_count;
	private int pixels;
	private int size;
	private int[][] answer_grid; // 0-10
	private int[][] input_grid; // -1, 0, 1, 2
	private LinkedList<Image> sprites;
	private boolean first = true;
	private int mines;
	private boolean finished = false;
//	private int time = 0;
//	private Thread timer;

	public Minesweeper(int tile_count, int pixels, int mines) {
		this.tile_count = tile_count;
		this.pixels = pixels;
		this.mines = mines;
		
		int size = tile_count * pixels;

		answer_grid = new int[tile_count][tile_count];
		input_grid = new int[tile_count][tile_count];
		sprites = new LinkedList<Image>();
		
		getSprites();
		
//		timer = new Thread(new Runnable() {
//			public void run() {
//				try {
//					while (true) {
//						if (first || finished)
//							time = 0;
//						time++;System.out.println(time);
//						Thread.sleep(1000);
//					}
//				} catch (InterruptedException e) {
//					System.out.println(e.getMessage());
//				}
//			}
//		});
//		timer.start();
		
		setSize(size + 12, size + 35);
		setTitle("Minesweeper");
		setIconImage(new ImageIcon("sprites/icon.png").getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addMouseListener(this);
	}

	public void getSprites() {
		sprites.add(0, new ImageIcon("sprites/empty_tile.png").getImage());
		sprites.add(1, new ImageIcon("sprites/one.png").getImage());
		sprites.add(2, new ImageIcon("sprites/two.png").getImage());
		sprites.add(3, new ImageIcon("sprites/three.png").getImage());
		sprites.add(4, new ImageIcon("sprites/four.png").getImage());
		sprites.add(5, new ImageIcon("sprites/five.png").getImage());
		sprites.add(6, new ImageIcon("sprites/six.png").getImage());
		sprites.add(7, new ImageIcon("sprites/seven.png").getImage());
		sprites.add(8, new ImageIcon("sprites/eight.png").getImage());
		sprites.add(9, new ImageIcon("sprites/wrong_mine.png").getImage());
		sprites.add(10, new ImageIcon("sprites/tile.png").getImage());
		sprites.add(11, new ImageIcon("sprites/flag.png").getImage());
		sprites.add(12, new ImageIcon("sprites/mine.png").getImage());
		sprites.add(13, new ImageIcon("sprites/red_mine.png").getImage());
	}
	
	public void generate(int x, int y) {
		int mines = this.mines;
		while (mines > 0) {
			int i = (int)(Math.random() * tile_count);
			int j = (int)(Math.random() * tile_count);
			
			if (!((i >= x - 2 && i <= x + 2) && (j >= y - 2 && j <= y + 2)) && answer_grid[i][j] == 0) {
				answer_grid[i][j] = 12;
				mines--;
			}
		}
		
		for (int i = 0; i < answer_grid.length; i++) {
			for (int j = 0; j < answer_grid[0].length; j++) {
				incrementTiles(i, j);
			}
		}
	}
	
	public void incrementTiles(int x, int y) {
		if (answer_grid[x][y] != 12) return;

		if (y > 0 && answer_grid[x][y - 1] != 12) {
			answer_grid[x][y - 1]++;
		}
		if (y < tile_count - 1 && answer_grid[x][y + 1] != 12) {
			answer_grid[x][y + 1]++;
		}
		if (x > 0 && answer_grid[x - 1][y] != 12) {
			answer_grid[x - 1][y]++;
		}
		if (x < tile_count - 1 && answer_grid[x + 1][y] != 12) {
			answer_grid[x + 1][y]++;
		}
		if (x > 0 && y > 0 && answer_grid[x - 1][y - 1] != 12) {
			answer_grid[x - 1][y - 1]++;
		}
		if (x < tile_count - 1 && y > 0 && answer_grid[x + 1][y - 1] != 12) {
			answer_grid[x + 1][y - 1]++;
		}
		if (x < tile_count - 1 && y < tile_count - 1 && answer_grid[x + 1][y + 1] != 12) {
			answer_grid[x + 1][y + 1]++;
		}
		if (y < tile_count - 1 && x > 0 && answer_grid[x - 1][y + 1] != 12) {
			answer_grid[x - 1][y + 1]++;
		}
	}
	
	public void onClick(int x, int y) {
		if (answer_grid[x][y] == 12) {
			finished = true;
			input_grid[x][y] = -1;
			return;
		}
		
		if (input_grid[x][y] == 1) return;
		
		input_grid[x][y] = 1;

		if (answer_grid[x][y] != 0) return;

		if (y > 0 && answer_grid[x][y - 1] != 12) {
			onClick(x, y - 1);
		}
		if (y < tile_count - 1 && answer_grid[x][y + 1] != 12) {
			onClick(x, y + 1);
		}
		if (x > 0 && answer_grid[x - 1][y] != 12) {
			onClick(x - 1, y);
		}
		if (x < tile_count - 1 && answer_grid[x + 1][y] != 12) {
			onClick(x + 1, y);
		}
		if (x > 0 && y > 0 && answer_grid[x - 1][y - 1] != 12) {
			onClick(x - 1, y - 1);
		}
		if (x < tile_count - 1 && y > 0 && answer_grid[x + 1][y - 1] != 12) {
			onClick(x + 1, y - 1);
		}
		if (x < tile_count - 1 && y < tile_count - 1 && answer_grid[x + 1][y + 1] != 12) {
			onClick(x + 1, y + 1);
		}
		if (y < tile_count - 1 && x > 0 && answer_grid[x - 1][y + 1] != 12) {
			onClick(x - 1, y + 1);
		}

	}
	
	public void tileClick(int x, int y) {
		if (y > 0 && input_grid[x][y - 1] != 2) {
			onClick(x, y - 1);
		}
		if (y < tile_count - 1 && input_grid[x][y + 1] != 2) {
			onClick(x, y + 1);
		}
		if (x > 0 && input_grid[x - 1][y] != 2) {
			onClick(x - 1, y);
		}
		if (x < tile_count - 1 && input_grid[x + 1][y] != 2) {
			onClick(x + 1, y);
		}
		if (x > 0 && y > 0 && input_grid[x - 1][y - 1] != 2) {
			onClick(x - 1, y - 1);
		}
		if (x < tile_count - 1 && y > 0 && input_grid[x + 1][y - 1] != 2) {
			onClick(x + 1, y - 1);
		}
		if (x < tile_count - 1 && y < tile_count - 1 && input_grid[x + 1][y + 1] != 2) {
			onClick(x + 1, y + 1);
		}
		if (y < tile_count - 1 && x > 0 && input_grid[x - 1][y + 1] != 2) {
			onClick(x - 1, y + 1);
		}
	}
	
	public int adjacentFlag(int x, int y) {
		int flags = 0;
		if (y > 0 && input_grid[x][y - 1] == 2) {
			flags++;
		}
		if (y < tile_count - 1 && input_grid[x][y + 1] == 2) {
			flags++;
		}
		if (x > 0 && input_grid[x - 1][y] == 2) {
			flags++;
		}
		if (x < tile_count - 1 && input_grid[x + 1][y] == 2) {
			flags++;
		}
		if (x > 0 && y > 0 && input_grid[x - 1][y - 1] == 2) {
			flags++;
		}
		if (x < tile_count - 1 && y > 0 && input_grid[x + 1][y - 1] == 2) {
			flags++;
		}
		if (x < tile_count - 1 && y < tile_count - 1 && input_grid[x + 1][y + 1] == 2) {
			flags++;
		}
		if (y < tile_count - 1 && x > 0 && input_grid[x - 1][y + 1] == 2) {
			flags++;
		}
		return flags;
	}
	
	public void rightClick(int x, int y) {
		input_grid[x][y] = input_grid[x][y] == 2 ? 0 : 2;
	}
	
	public void restart() {
		answer_grid = new int[tile_count][tile_count];
		input_grid = new int[tile_count][tile_count];
		first = true;
		finished = false;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int i = 0; i < answer_grid.length; i++) {
			for (int j = 0; j < answer_grid[0].length; j++) {
				if (finished) {
					if (input_grid[i][j] == -1)
						g.drawImage(sprites.get(13), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
					else if (input_grid[i][j] == 2)
						if (answer_grid[i][j] == 12)
							g.drawImage(sprites.get(11), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
						else
							g.drawImage(sprites.get(9), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
					else
						g.drawImage(sprites.get(answer_grid[i][j]), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
//					g.drawString("Time: " + time, size / 2, size / 2);
				}
				else if (input_grid[i][j] == 1)
					g.drawImage(sprites.get(answer_grid[i][j]), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
				else if (input_grid[i][j] == 2)
					g.drawImage(sprites.get(11), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
				else
					g.drawImage(sprites.get(10), i * pixels + 7, j * pixels + 30, pixels, pixels, null);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = (e.getX() - 7) / pixels;
		int y = (e.getY() - 30) / pixels;
		
//		if (e.getButton() == MouseEvent.BUTTON2) {
//			restart();
//			return;
//		}
		if (finished) { 
			restart();
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON3 && (input_grid[x][y] == 0 || input_grid[x][y] == 2)) {
			rightClick(x, y);
			repaint();
			return;
		}
		if (first) {
			generate(x, y);
			first = false;
		}
		if (input_grid[x][y] == 1) {
			if (adjacentFlag(x, y) != answer_grid[x][y]) return;
			tileClick(x, y);
			repaint();
		}
		else if (input_grid[x][y] == 2);
		else {
			onClick(x, y);
			repaint();
		}
		checkFinished();
	}
	
	public void checkFinished() {
		int tiles = tile_count * tile_count - mines;
		int clicked = 0;
		for (int i = 0; i < input_grid.length; i++) {
			for (int j = 0; j < input_grid[0].length; j++) {
				if (input_grid[i][j] == 1) clicked++;
			}
		}
		if (clicked == tiles) finished = true;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
		
	public static void main(String[] args) {
		// 20 30 50
		new Minesweeper(20, 30, 50);
	}
	
}