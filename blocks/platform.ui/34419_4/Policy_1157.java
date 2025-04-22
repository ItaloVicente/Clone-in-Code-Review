package org.eclipse.ui.internal.misc;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.internal.WorkbenchImages;

public class ExternalProgramImageDescriptor extends ImageDescriptor {

    public Program program;

    public ExternalProgramImageDescriptor(Program program) {
        this.program = program;
    }

    @Override
	public boolean equals(Object o) {
        if (!(o instanceof ExternalProgramImageDescriptor)) {
            return false;
        }
        ExternalProgramImageDescriptor other = (ExternalProgramImageDescriptor) o;

        String otherName = other.program.getName();
        if (otherName == null) {
			return other.program.equals(program);
		} else {
            return otherName.equals(program.getName());
        }
    }

    public Image getImage() {
        return createImage();
    }

    @Override
	public ImageData getImageData() {
        ImageData data = null;
        ImageData defaultImage = WorkbenchImages.getImageDescriptor(
                ISharedImages.IMG_OBJ_FILE).getImageData();
        if (defaultImage == null) {
			return null;
		}

        if (program == null || ((data = program.getImageData()) == null)) {
			return defaultImage;
		}

        if (data.height > defaultImage.height
                || data.width > defaultImage.width) {
			return defaultImage;
		}

        return data;
    }

    @Override
	public int hashCode() {
        String programName = program.getName();
        if (programName == null) {
			return program.hashCode();
		} else {
			return programName.hashCode();
		}
    }
}
