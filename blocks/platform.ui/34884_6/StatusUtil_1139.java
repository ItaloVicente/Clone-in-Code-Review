package org.eclipse.ui.internal.misc;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.internal.WorkbenchImages;

public class ProgramImageDescriptor extends ImageDescriptor {
    private String filename;

    private int offset;

    public ProgramImageDescriptor(String fullPath, int offsetInFile) {
        filename = fullPath;
        offset = offsetInFile;
    }

    @Override
	public boolean equals(Object o) {
        if (!(o instanceof ProgramImageDescriptor)) {
            return false;
        }
        ProgramImageDescriptor other = (ProgramImageDescriptor) o;
        return filename.equals(other.filename) && offset == other.offset;
    }

    public Image getImage() {
        return createImage();
    }

    @Override
	public ImageData getImageData() {

        return WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE)
                .getImageData();
    }

    @Override
	public int hashCode() {
        return filename.hashCode() + offset;
    }
}
