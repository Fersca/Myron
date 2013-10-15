package detector;

import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class MoveDetectorListener implements WebcamMotionListener {

	public String camara;
	
	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		if (wme.getStrength()>100000)
			System.out.println("Detected "+wme.getStrength()+" motion In camera: "+camara);
	}

}