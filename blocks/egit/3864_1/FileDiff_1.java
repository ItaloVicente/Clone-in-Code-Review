package org.eclipse.egit.ui.internal;

import java.util.Arrays;

import org.eclipse.egit.ui.UIUtils;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class DecorationOverlayDescriptor extends CompositeImageDescriptor {

	private ImageDescriptor base;

	private ImageDescriptor[] overlays;

	private Point size;

	public DecorationOverlayDescriptor(ImageDescriptor baseImage,
			ImageDescriptor[] overlaysArray, Point sizeValue) {
		this.base = baseImage;
		this.overlays = overlaysArray;
		this.size = sizeValue;
	}

	public DecorationOverlayDescriptor(ImageDescriptor baseImage,
			ImageDescriptor[] overlaysArray) {
		this(baseImage, overlaysArray, UIUtils.getSize(baseImage));
	}

	public DecorationOverlayDescriptor(ImageDescriptor baseImage,
			ImageDescriptor overlayImage, int quadrant) {
		this(baseImage, createArrayFrom(overlayImage, quadrant));
	}

	private static ImageDescriptor[] createArrayFrom(
			ImageDescriptor overlayImage, int quadrant) {
		ImageDescriptor[] descs = new ImageDescriptor[] { null, null, null,
				null, null };
		descs[quadrant] = overlayImage;
		return descs;
	}

	private void drawOverlays(ImageDescriptor[] overlaysArray) {
		for (int i = 0; i < overlays.length; i++) {
			ImageDescriptor overlay = overlaysArray[i];
			if (overlay == null)
				continue;
			ImageData overlayData = overlay.getImageData();
			if (overlayData == null)
				overlayData = ImageDescriptor.getMissingImageDescriptor()
						.getImageData();

			switch (i) {
			case IDecoration.TOP_LEFT:
				drawImage(overlayData, 0, 0);
				break;
			case IDecoration.TOP_RIGHT:
				drawImage(overlayData, size.x - overlayData.width, 0);
				break;
			case IDecoration.BOTTOM_LEFT:
				drawImage(overlayData, 0, size.y - overlayData.height);
				break;
			case IDecoration.BOTTOM_RIGHT:
				drawImage(overlayData, size.x - overlayData.width, size.y
						- overlayData.height);
				break;
			}
		}
	}

	public boolean equals(Object o) {
		if (!(o instanceof DecorationOverlayDescriptor))
			return false;
		DecorationOverlayDescriptor other = (DecorationOverlayDescriptor) o;
		return base.equals(other.base)
				&& Arrays.equals(overlays, other.overlays);
	}

	public int hashCode() {
		int code = System.identityHashCode(base);
		for (int i = 0; i < overlays.length; i++)
			if (overlays[i] != null)
				code ^= overlays[i].hashCode();
		return code;
	}

	protected void drawCompositeImage(int width, int height) {
		if (overlays.length > IDecoration.UNDERLAY) {
			ImageDescriptor underlay = overlays[IDecoration.UNDERLAY];
			if (underlay != null)
				drawImage(underlay.getImageData(), 0, 0);
		}

		if (overlays.length > IDecoration.REPLACE
				&& overlays[IDecoration.REPLACE] != null)
			drawImage(overlays[IDecoration.REPLACE].getImageData(), 0, 0);
		else
			drawImage(getBaseImageData(), 0, 0);

		drawOverlays(overlays);
	}

	private ImageData getBaseImageData() {
		final ImageData data = base.getImageData();
		return data != null ? data : DEFAULT_IMAGE_DATA;
	}

	protected Point getSize() {
		return size;
	}

	protected int getTransparentPixel() {
		return getBaseImageData().transparentPixel;
	}
}
