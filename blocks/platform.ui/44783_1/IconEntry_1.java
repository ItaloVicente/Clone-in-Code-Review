
package org.eclipse.ui.images.renderer;

import java.io.File;
 
class IconEntry implements Comparable<IconEntry> {

    String nameBase;

    File inputPath;

    int[] sizes;

    File outputPath;

    File disabledPath;

    public IconEntry(String nameBase, File inputPath, File outputPath,
            File disabledPath, int[] sizes) {
        this.nameBase = nameBase;
        this.sizes = sizes;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.disabledPath = disabledPath;
    }

	public int compareTo(IconEntry o) {
		return nameBase.compareTo(o.nameBase);
	}
}
