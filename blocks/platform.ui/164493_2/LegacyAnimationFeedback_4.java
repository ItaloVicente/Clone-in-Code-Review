
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public abstract class ImageCycleFeedbackBase extends AnimationFeedbackBase {
	protected Image[] images;
	protected Image stoppedImage;
	private Image offScreenImage;
	private GC offScreenImageGC;
	private int imageDataIndex;
	private Image image;
	private ImageData imageData;
	protected Display display;
	protected Color background;

	public ImageCycleFeedbackBase(Shell parentShell) {
		super(parentShell);
	}

	public ImageCycleFeedbackBase(Shell parentShell, Image[] images) {
		super(parentShell);
		this.images = images;
	}

	public abstract void showImage(Image image);

	public abstract void saveStoppedImage();

	public abstract void setStoppedImage(Image image);

	@Override
	public void dispose() {
		if (stoppedImage == null || stoppedImage.isDisposed())
			return;
		setStoppedImage(stoppedImage);

		if (offScreenImageGC != null && !offScreenImageGC.isDisposed())
			offScreenImageGC.dispose();

		if (offScreenImage != null && !offScreenImage.isDisposed())
			offScreenImage.dispose();
	}

	@Override
	public boolean jobInit(AnimationEngine engine) {
		return super.jobInit(engine);
	}

	@Override
	public void renderStep(AnimationEngine engine) {
		if (offScreenImage == null) {
			offScreenImage = getOffscreenImage();
		}

		try {
			imageDataIndex = (imageDataIndex + 1) % images.length;
			image = images[imageDataIndex];
			imageData = image.getImageData();

			offScreenImageGC.drawImage(image, 0, 0, imageData.width, imageData.height, imageData.x, imageData.y,
					imageData.width, imageData.height);

			final Image finalImage = image;

			display.syncExec(() -> showImage(finalImage));

			if (images == null)
				return;
		} catch (SWTException ex) {
			IStatus status = StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH, ex);
			StatusManager.getManager().handle(status);
		}
	}

	private Image getOffscreenImage() {
		saveStoppedImage();
		imageDataIndex = 0;
		image = images[imageDataIndex];
		imageData = image.getImageData();
		offScreenImage = new Image(display, imageData.width, imageData.height);

		offScreenImageGC = new GC(offScreenImage);
		offScreenImageGC.setBackground(background);
		offScreenImageGC.fillRectangle(0, 0, imageData.width, imageData.height);


		offScreenImageGC.drawImage(image, 0, 0, imageData.width, imageData.height, imageData.x, imageData.y,
				imageData.width, imageData.height);

		return offScreenImage;
	}

}
