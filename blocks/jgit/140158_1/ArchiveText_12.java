package org.eclipse.jgit.archive;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.archive.internal.ArchiveText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;

public final class ZipFormat extends BaseFormat implements
		ArchiveCommand.Format<ArchiveOutputStream> {
	private static final List<String> SUFFIXES = Collections

	@Override
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s)
			throws IOException {
		return createArchiveOutputStream(s
				Collections.<String
	}

	@Override
	public ArchiveOutputStream createArchiveOutputStream(OutputStream s
			Map<String
		return applyFormatOptions(new ZipArchiveOutputStream(s)
	}

	@Override
	public void putEntry(ArchiveOutputStream out
			ObjectId tree
			throws IOException {
			throw new IllegalArgumentException(MessageFormat.format(
					ArchiveText.get().pathDoesNotMatchMode

		final ZipArchiveEntry entry = new ZipArchiveEntry(path);

		if (tree instanceof RevCommit) {
			long t = ((RevCommit) tree).getCommitTime() * 1000L;
			entry.setTime(t);
		}

		if (mode == FileMode.TREE) {
			out.putArchiveEntry(entry);
			out.closeArchiveEntry();
			return;
		}

		if (mode == FileMode.REGULAR_FILE) {
		} else if (mode == FileMode.EXECUTABLE_FILE
				|| mode == FileMode.SYMLINK) {
			entry.setUnixMode(mode.getBits());
		} else {
			throw new IllegalArgumentException(MessageFormat.format(
					ArchiveText.get().unsupportedMode
		}
		entry.setSize(loader.getSize());
		out.putArchiveEntry(entry);
		loader.copyTo(out);
		out.closeArchiveEntry();
	}

	@Override
	public Iterable<String> suffixes() {
		return SUFFIXES;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof ZipFormat);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
