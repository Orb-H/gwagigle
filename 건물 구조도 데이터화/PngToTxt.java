import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PngToTxt {

	public static final Color target = new Color(237, 231, 218);
	public static final int target_int = target.getRGB();

	public static final int width = 3;
	public static final int height = 2;

	public static void main(String[] args) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File("D:\\Documents\\GitHub\\gwagigle/5th floor.png"));// Fill in
			File output = new File("D:\\Documents\\GitHub\\gwagigle/floor5.txt");
			FileOutputStream fos = new FileOutputStream(output);

			fos.write(String.format("%d", bi.getWidth() / width + 2).getBytes());
			fos.write(' ');
			fos.write(String.format("%d", bi.getHeight() / height + 2).getBytes());
			fos.write('\n');
			fos.flush();

			for (int j = 0; j < bi.getWidth() / width + 2; j++) {
				fos.write('1');
				fos.write(' ');
			}
			fos.write('\n');

			for (int i = 0; i < bi.getHeight() / height; i++) {
				fos.write('1');
				fos.write(' ');
				for (int j = 0; j < bi.getWidth() / width; j++) {
					boolean isCorridor = false;
					for (int ii = 0; ii < height; ii++) {
						for (int jj = 0; jj < width; jj++) {
							int tmp = bi.getRGB(j * width + jj, i * height + ii);
							if (tmp == target_int)
								isCorridor = true;
						}
					}

					if (isCorridor)
						fos.write('0');
					else {
						fos.write('1');
					}
					fos.write(' ');
				}
				fos.write('1');
				fos.write('\n');
			}

			for (int j = 0; j < bi.getWidth() / width + 2; j++) {
				fos.write('1');
				fos.write(' ');
			}

			fos.flush();
			fos.close();
		} catch (IOException e) {
			return;
		}

	}

}
