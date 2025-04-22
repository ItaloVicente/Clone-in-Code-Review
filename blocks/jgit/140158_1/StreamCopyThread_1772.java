
package org.eclipse.jgit.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SilentFileInputStream extends FileInputStream {
	public SilentFileInputStream(File file) throws FileNotFoundException {
		super(file);
	}

	@Override
	public void close() {
		try {
			super.close();
		} catch (IOException e) {
		}
	}
}
