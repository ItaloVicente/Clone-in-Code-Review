package org.eclipse.jgit.pgm.archive;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;

class TarFormat implements ArchiveCommand.Format {
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
		return new TarArchiveOutputStream(s);
	}

	public void putEntry(String path
				ArchiveOutputStream out) throws IOException {
		if (mode == FileMode.SYMLINK) {
			final TarArchiveEntry entry = new TarArchiveEntry(
					path
			entry.setLinkName(new String(
					loader.getCachedBytes(100)
			out.putArchiveEntry(entry);
			out.closeArchiveEntry();
			return;
		}

		final TarArchiveEntry entry = new TarArchiveEntry(path);
		if (mode == FileMode.REGULAR_FILE ||
		    mode == FileMode.EXECUTABLE_FILE) {
			entry.setMode(mode.getBits());
		} else {
		}
		entry.setSize(loader.getSize());
		out.putArchiveEntry(entry);
		loader.copyTo(out);
		out.closeArchiveEntry();
	}
}
