package org.eclipse.jgit.pgm.archive;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;

class ZipFormat implements ArchiveCommand.Format {
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
		return new ZipArchiveOutputStream(s);
	}

	public void putEntry(String path
				ArchiveOutputStream out) throws IOException {
		final ZipArchiveEntry entry = new ZipArchiveEntry(path);

		if (mode == FileMode.REGULAR_FILE) {
		} else if (mode == FileMode.EXECUTABLE_FILE
				|| mode == FileMode.SYMLINK) {
			entry.setUnixMode(mode.getBits());
		} else {
		}
		entry.setSize(loader.getSize());
		out.putArchiveEntry(entry);
		loader.copyTo(out);
		out.closeArchiveEntry();
	}
}
