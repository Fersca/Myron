package detector.colors;

import java.util.List;

class Bloque {
	public Bloque(int row, int col) {
		this.row=row;
		this.col=col;
	}
	public int row;
	public int col;
	public int red=0;
	public int green=0;
	public int blue=0;
	public String status;
	public String statusAprox;
	public int color=0;
	public Color colorAprox;
	
	private static List<Color> listaColores = Colors.createListaColores();
	
	public void verificar(String colorDetectar) {

		colorAprox = getColorAprox(red, green, blue);
		
		String colorHexa = completar(Integer.toHexString(colorAprox.red))+completar(Integer.toHexString(colorAprox.green))+completar(Integer.toHexString(colorAprox.blue));
		
		if (colorAprox.nombre.equals(colorDetectar))
			color = 16711680; //rojo
		else 
			color = convert(colorHexa.toUpperCase()); 
			

	}

	private String completar(String s){
		if (s.length()==2) 
			return s;
		else
			return "0"+s;
	}
	
	private int convert(String colorH){

		//convierto a entero el valor en hexa
		int valor = 0;
		int tamanio = colorH.length();
		
		for (int i =0;i<tamanio;i++){
			valor = valor + Integer.parseInt(""+colorH.charAt(tamanio-1-i), 16) * potencia16(i);
		}
		return valor;

	}

	private int potencia16(int i) {

		if (i==0) return 1;
		
		int valor = 16;
		
		for(int j = i;j>1;j--){
			valor = valor * 16;
		}
		
		return valor;
	}
	
	private Color getColorAprox(int r, int g, int b) {

		Color menor=null;
		double dif=1000000;
		for (Color c : listaColores) {
			double diferencia = c.difference(r, g, b);
			if (diferencia<dif){
				dif = diferencia;
				menor = c;
			}
			
		}
		return menor;
	}
}
