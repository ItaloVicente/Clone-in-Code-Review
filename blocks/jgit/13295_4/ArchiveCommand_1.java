package org.eclipse.jgit.archive;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;

public class TxzFormat implements ArchiveCommand.Format<ArchiveOutputStream> {
	private final ArchiveCommand.Format<ArchiveOutputStream> tarFormat = new TarFormat();

	public ArchiveOutputStream createArchiveOutputStream(OutputStream s)
			throws IOException {
		XZCompressorOutputStream out = new XZCompressorOutputStream(s);
		return tarFormat.createArchiveOutputStream(out);
	}

	public void putEntry(ArchiveOutputStream out
			String path
			throws IOException {
		tarFormat.putEntry(out
	}
}
