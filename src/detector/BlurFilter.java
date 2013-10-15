package detector;

import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.util.jh.JHBlurFilter;

public class BlurFilter implements WebcamImageTransformer {

	private static final JHBlurFilter BLUR = new JHBlurFilter(3,3,3);
	
	@Override
	public BufferedImage transform(BufferedImage image) {
		return BLUR.filter(image, null);
	}

}
