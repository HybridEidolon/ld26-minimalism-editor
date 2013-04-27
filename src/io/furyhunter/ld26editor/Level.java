package io.furyhunter.ld26editor;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Level {
	private List<Entity> entities;
	
	public int offx;
	public int offy;
	
	public Level() {
		entities = new LinkedList<Entity>();
	}
	
	public Level(File f) throws FileNotFoundException {
		entities = new LinkedList<Entity>();
		Scanner s = new Scanner(new FileInputStream(f));
		
		while (s.hasNext()) {
			String type = s.next();
			if (type.equals("Tile")) {
				Tile t = new Tile();
				t.x = s.nextFloat();
				t.y = s.nextFloat();
				entities.add(t);
			}
			if (type.equals("Player")) {
				Player p = new Player();
				p.x = s.nextFloat();
				p.y = s.nextFloat();
				entities.add(p);
			}
		}
		
		System.out.printf("%d entities found\n", entities.size());
	}
	
	public void save(File p) throws FileNotFoundException {
		PrintWriter print = new PrintWriter(new FileOutputStream(p));
		
		for (Entity e : entities) {
			e.save(print);
		}
		print.flush();
		print.close();
	}
	
	public void draw(Graphics g) {
		for (Entity e : entities) {
			e.draw(g, offx, offy);
		}
	}
	
	public Class remove(int x, int y) {
		Entity toRemove = null;
		for (Entity e : entities) {
			if (e.touch(x, y)) {
				toRemove = e;
				break;
			}
		}
		
		entities.remove(toRemove);
		
		return toRemove == null ? null : toRemove.getClass();
	}
	
	public void add(EntityTypes type, int x, int y) {
		Entity en = null;
		switch (type) {
		case Player:
			en = new Player();
			en.x = x;
			en.y = y;
			break;
		case Tile:
			en = new Tile();
			en.x = x;
			en.y = y;
			break;
		}
		
		entities.add(en);
	}
}
