package org.eclipse.ui.internal;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.internal.util.Util;

public class OverlayIcon extends CompositeImageDescriptor {

    private Point fSize = null;

    private ImageDescriptor fBase = null;

    private ImageDescriptor fOverlay = null;

    public OverlayIcon(ImageDescriptor base, ImageDescriptor overlay, Point size) {
        fBase = base;
        fOverlay = overlay;
        fSize = size;
    }

    @Override
	protected void drawCompositeImage(int width, int height) {
        ImageData bg;
        if (fBase == null || (bg = fBase.getImageData()) == null) {
			bg = DEFAULT_IMAGE_DATA;
		}
        drawImage(bg, 0, 0);

        if (fOverlay != null) {
			drawTopRight(fOverlay);
		}
    }

    protected void drawTopRight(ImageDescriptor overlay) {
        if (overlay == null) {
			return;
		}
        int x = getSize().x;
        ImageData id = overlay.getImageData();
        x -= id.width;
        drawImage(id, x, 0);
    }

    @Override
	protected Point getSize() {
        return fSize;
    }

    @Override
	public int hashCode() {
        return Util.hashCode(fBase) * 17 + Util.hashCode(fOverlay);
    }

    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof OverlayIcon)) {
			return false;
		}
        OverlayIcon overlayIcon = (OverlayIcon) obj;
        return Util.equals(this.fBase, overlayIcon.fBase)
                && Util.equals(this.fOverlay, overlayIcon.fOverlay)
                && Util.equals(this.fSize, overlayIcon.fSize);
    }
}
