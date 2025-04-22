package org.eclipse.jgit.archive;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;

public final class Tbz2Format extends BaseFormat implements
		ArchiveCommand.Format<ArchiveOutputStream> {
	private static final List<String> SUFFIXES = Collections
			.unmodifiableList(Arrays.asList(".tar.bz2"

	private final ArchiveCommand.Format<ArchiveOutputStream> tarFormat = new TarFormat();

	@Override
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s)
			throws IOException {
		return createArchiveOutputStream(s
				Collections.<String
	}

	@Override
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s
			Map<String
		BZip2CompressorOutputStream out = new BZip2CompressorOutputStream(s);
		return tarFormat.createArchiveOutputStream(out
	}

	@Override
	public void putEntry(ArchiveOutputStream out
			ObjectId tree
			throws IOException {
		tarFormat.putEntry(out
	}

	@Override
	public Iterable<String> suffixes() {
		return SUFFIXES;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof Tbz2Format);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
