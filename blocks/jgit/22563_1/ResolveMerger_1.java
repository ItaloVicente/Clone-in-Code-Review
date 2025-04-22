package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.lib.Config;

public class BinaryMergeDriver implements MergeDriver {
	public boolean merge(Config configuration
			InputStream theirs
			String[] commitNames) throws IOException {
		byte[] buffer = new byte[8192];
		int read = ours.read(buffer);
		while (read > 0) {
			output.write(buffer
			read = ours.read(buffer);
		}
		return false;
	}

	public String getName() {
	}
}
