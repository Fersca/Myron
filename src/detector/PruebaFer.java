package detector;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class PruebaFer {

	public static void main(String[] args) throws InterruptedException {

		List<Webcam> camaras = Webcam.getWebcams();

		for (Webcam webcam : camaras) {
			
			//setea la resolucion de la camara
			webcam.setViewSize(WebcamResolution.VGA.getSize());	
			
			//Aplica el filtro de blur
			webcam.setImageTransformer(new BlurFilter());
			
			//panel con la imagen
			ImagePanel panel2 = new ImagePanel(new ImageIcon("/home/fersca/code/Myron/src/prueba5.png").getImage());
			ImagePanel panel3 = new ImagePanel(new ImageIcon("/home/fersca/code/Myron/src/prueba5.png").getImage());
			ImageListener imaLis = new ImageListener();
			imaLis.panel = panel2;
			imaLis.panel2 = panel3;
			imaLis.TAMANO_BLOQUE = 80; // 160max
			imaLis.color="VE";
						
			//Agrega un listener de procesamiento de imagenes.
			webcam.addWebcamListener(imaLis);
			
			//Le agrega un detector de movimiento
			WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
			detector.setInterval(1000); 
			
			//Crea un nuevo listener de movimiento y lo linkea al detector
			MoveDetectorListener moveListener = new MoveDetectorListener();
			moveListener.camara=webcam.getName();
			detector.addMotionListener(moveListener);
			detector.start();
			
			//Crea el panel para la camara
			WebcamPanel panel = new WebcamPanel(webcam);
			panel.setFPSDisplayed(false);
			panel.setFillArea(true);
				
			//Crea el frame para poner el panel
			JFrame window = new JFrame(webcam.getName());
			window.add(panel);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.pack();
			window.setVisible(true);	
			
			//Crea el segundo frame para poner el panel de la imagen
			JFrame window2 = new JFrame("Imagen");
		    window2.add(panel2);
		    window2.setVisible(true);
		    window2.setSize(640,480);
		    
			//Crea el segundo frame para poner solo los pixels detectados
			JFrame window3 = new JFrame("Imagen");
		    window3.add(panel3);
		    window3.setVisible(true);
		    window3.setSize(640,480);
			
		}
			
	}
	
}
