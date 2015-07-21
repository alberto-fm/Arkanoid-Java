import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class main extends JFrame implements KeyListener {

private int anchoVentana = 600;
private int altoVentana = 400;

private int anchoRaqueta = 60;
private int altoRaqueta = 15;
private int velRaqueta = 5;
	
private int velPelotaX = 5;
private int velPelotaY = 5;
private int ladoPelota = 20;

private Paleta raqueta = new Paleta(anchoVentana/2 - anchoRaqueta/2, altoVentana - altoRaqueta, anchoRaqueta, altoRaqueta, velRaqueta);
private Pelota pelota = new Pelota(anchoVentana/2 - ladoPelota/2, altoVentana - altoRaqueta - ladoPelota, velPelotaX, velPelotaY, ladoPelota);
//Recordar pintar los obstaculos con un array/lista...

private int key = 0;
private long tiempoDeRun;
private long tiempoDeSleep = 20;
private boolean run = true;
//private int tiempoDeEspera = 1000;

public static void main (String[] args) {
	new main();
}


public main() {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(anchoVentana, altoVentana);
	this.setResizable(false);
	this.setLocation(100, 100);
	this.setVisible(true);


	while (true) {
	if (!run)
		try {
			Thread.sleep(500);
			run = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		while (run) {
			calcularColisiones();
			desplazamientoRaqueta();
			desplazamientoPelota();
			reiniciarJuego();
			pintar();
			sleep();
		}
	}
}

private void calcularColisiones() {
	//Colisiones de la pelota con las paredes; superior, izquierda y derecha, faltan colisiones con los obstaculos
	if(pelota.y <= 0) {
		velPelotaY = -velPelotaY;
	}
	if(pelota.x <=0 || pelota.x >= anchoVentana - ladoPelota) {
		velPelotaX = -velPelotaX;
	}
}

private void desplazamientoRaqueta() {
	//movimiento raqueta en eje x
	if (raqueta.x < anchoVentana - anchoRaqueta && key==KeyEvent.VK_RIGHT) {
		velRaqueta = raqueta.x + velRaqueta;
	}
	if (raqueta.x > 0 && key==KeyEvent.VK_LEFT) {
		velRaqueta = raqueta.x - velRaqueta;
	}
}

private void desplazamientoPelota() {
	pelota.x += velPelotaX;
	pelota.y -= velPelotaY;
}

private void reiniciarJuego() {
	if(pelota.y > altoVentana) {
		raqueta.x = anchoVentana/2 - anchoRaqueta/2;
		raqueta.y =	altoVentana - altoRaqueta;	
		pelota.x = anchoVentana/2 - ladoPelota/2;
		pelota.y = altoVentana - altoRaqueta - ladoPelota;
		//La lista de obstaculos tb debe volver a su pos inicial
		run = false;
	}
}

private void pintar() {
	BufferStrategy bf = this.getBufferStrategy();
	Graphics g = null;
	
	try {
		g = bf.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, anchoVentana, altoVentana);
		
		//Pintamos la pelota
		g.setColor(Color.WHITE);
		g.fillOval(pelota.x, pelota.y, pelota.ladoPelota, pelota.ladoPelota);
		
		//Pintar paleta
		g.fillRect(raqueta.x, raqueta.y, raqueta.ancho, raqueta.alto);
		
		//pintar obstaculos tb 
	} finally {
		g.dispose();
	}
	bf.show();
	
	Toolkit.getDefaultToolkit().sync();
}

private void sleep() {
	tiempoDeRun = ( System.currentTimeMillis() + tiempoDeSleep );
	while(System.currentTimeMillis() < tiempoDeRun) {
		
	}
}

@Override
public void keyPressed(KeyEvent e) {
	key = e.getKeyCode(); 
}

@Override
public void keyReleased(KeyEvent e) {
	key = 0;
}

@Override
public void keyTyped(KeyEvent e) {
	
}






}