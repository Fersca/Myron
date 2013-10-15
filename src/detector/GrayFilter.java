package detector;

import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;

public class GrayFilter implements WebcamImageTransformer {

	private static final JHGrayFilter GRAY = new JHGrayFilter();
	
	@Override
	public BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}

}
