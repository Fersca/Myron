package detector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamEventType;
import com.github.sarxos.webcam.WebcamListener;

import detector.colors.ColorDetected;
import detector.colors.ColorDetector;
import detector.colors.ImageInfo;

public class ImageListener implements WebcamListener{

	@Override
	public void webcamOpen(WebcamEvent we) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		// TODO Auto-generated method stub
		
	}

	ColorDetector detector = new ColorDetector();
	public ImagePanel panel;
	public ImagePanel panel2;
	public int TAMANO_BLOQUE;
	public String color;
	
	@Override
	public void webcamImageObtained(WebcamEvent we) {
		
		//Si el evento es una imagen nueva puedo procesarla
		if (we.getType()==WebcamEventType.NEW_IMAGE){
			
			//Obtengo la imagen de la camara
			BufferedImage imagen = we.getImage();
			
			//Detecto los colores
			ImageInfo infoImage=null;
			try {
				infoImage= detector.detectColors(imagen, TAMANO_BLOQUE, color);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Obtiene los colores detectados
			ArrayList<ColorDetected> colores = infoImage.detectados;
			
			//Si detecta el color verde con un porcentaje considerable, lo informa
			boolean detected=false;
			for (ColorDetected colorDetected : colores) {
				if (colorDetected.nombre.equals(color)&&colorDetected.porcentage>5){
					detected=true;
					System.out.println(colorDetected.nombre + "---" +colorDetected.porcentage);
				}
			}
			
			//calcula las cantidad de pixels donde se detecto color
			int cantidad=0;
			int sumaI=0;
			int sumaJ=0;
			
			for (int i=0;i<infoImage.bloque;i++){
				for (int j=0;j<infoImage.bloque;j++){
					if (infoImage.matriz[j][i]==16711680){
						sumaJ=sumaJ+j;
						sumaI=sumaI+i;
						cantidad++;
					}
				}
			}
		
			//Si se dectecto suficiente color, detecta el objeto
			if (cantidad!=0&&detected){
				
				//calcula la posicion promedio de x,y
				sumaI=sumaI/cantidad;
				sumaJ=sumaJ/cantidad;
				
				//imprime el tablero en ascii
				imprimeASCII(infoImage, sumaI, sumaJ);
				
				//Coordenadas del centeo del objeto
				System.out.println("I="+sumaI+",J="+sumaJ);
				
				//pinta un cuadrado en el centro del objeto
				pintaCentro(infoImage, sumaI, sumaJ);	
			
			}
						
			//asigna la imagen nueva al panel y la repinta
			panel.img=infoImage.image;
			panel.repaint();
			
			BufferedImage img2 = marcaCentro(infoImage,sumaI, sumaJ);
			
			panel2.img=img2;
			panel2.repaint();
		}
	}

	private BufferedImage marcaCentro(ImageInfo infoImage, int sumaI, int sumaJ) {

		int inicioI = (640/infoImage.bloque)*sumaI;
		int inicioJ = (480/infoImage.bloque)*sumaJ;
		
		BufferedImage img2 =new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		for (int i=inicioI;i<inicioI+40;i++){
			for (int j=inicioJ;j<inicioJ+40;j++){
				img2.setRGB(i, j, 65280); //pinta de verde
			}				
		}
		
		return img2;
	}

	private void pintaCentro(ImageInfo infoImage, int sumaI, int sumaJ) {
		int inicioI = (640/infoImage.bloque)*sumaI;
		int inicioJ = (480/infoImage.bloque)*sumaJ;
		
		for (int i=inicioI;i<inicioI+40;i++){
			for (int j=inicioJ;j<inicioJ+40;j++){
				infoImage.image.setRGB(i, j, 65280); //pinta de verde
			}				
		}
	}

	private void imprimeASCII(ImageInfo infoImage, int sumaI, int sumaJ) {
		//separador de impresion
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		//imprime en ascii la imagen con e objeto detectado
		for (int i=0;i<infoImage.bloque;i++){
			for (int j=0;j<infoImage.bloque;j++){
				
				if(sumaI==i && sumaJ==j){
					System.out.print("O");
				} else {
					if (infoImage.matriz[j][i]==16711680)						
						System.out.print("*");
					else
						System.out.print("-");						
				}
				
			}
			System.out.println("");
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
