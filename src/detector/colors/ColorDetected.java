package detector.colors;

public class ColorDetected implements Comparable<ColorDetected> {

	public String nombre;
	public int porcentage;
	int orden;
	
	public ColorDetected(String n, int p, int o) {
		nombre=n;
		porcentage=p;
		orden=o;
	}

	@Override
	public int compareTo(ColorDetected otro) {
		return otro.porcentage - this.porcentage;
	}

}
