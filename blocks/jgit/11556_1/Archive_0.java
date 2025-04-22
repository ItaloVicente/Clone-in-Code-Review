package org.eclipse.jgit.archive;

import java.lang.String;
import java.lang.System;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;
import java.text.MessageFormat;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.CLIText;
import org.eclipse.jgit.treewalk.TreeWalk;

public class ArchiveCommand extends GitCommand<OutputStream> {

	private OutputStream out = null;

	private TreeWalk walk = new TreeWalk(repo);

	private Format format = Format.TAR;

	public static enum Format {
		ZIP
		TAR
	}

	public ArchiveCommand(Repository repo) {
		super(repo);
	}

	public OutputStream call() {
		final ObjectReader reader = walk.getObjectReader();
		final MutableObjectId idBuf = new MutableObjectId();
		final Archiver fmt = formats.get(format);
		final ArchiveOutputStream outa = fmt.createArchiveOutputStream(out);

		walk.setRecursive(true);
		while (walk.next()) {
			final String name = walk.getPathString();
			final FileMode mode = walk.getFileMode(0);

			if (mode == FileMode.TREE)
				continue;

			walk.getObjectId(idBuf
			fmt.putEntry(name
		}
		outa.close();
		return out;
	}

	public ArchiveCommand setTree(ObjectId tree) {
		walk.reset();
		walk.addTree(tree);
		return this;
	}

	public ArchiveCommand setOutputStream(OutputStream out) {
		this.out = out;
		return this;
	}

	public ArchiveCommand setFormat(Format fmt) {
		this.format = fmt;
		return this;
	}


	static private void warnArchiveEntryModeIgnored(String name) {
				CLIText.get().archiveEntryModeIgnored
				name));
	}

	private static interface Archiver {
		ArchiveOutputStream createArchiveOutputStream(OutputStream s);
		void putEntry(String path
				ObjectLoader loader
				throws IOException;
	}

	private static final Map<Format
	static {
		Map<Format
		fmts.put(Format.ZIP
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new ZipArchiveOutputStream(s);
			}

			public void putEntry(String path
					ObjectLoader loader
					throws IOException {
				final ZipArchiveEntry entry = new ZipArchiveEntry(path);

				if (mode == FileMode.REGULAR_FILE) {
				} else if (mode == FileMode.EXECUTABLE_FILE
						|| mode == FileMode.SYMLINK) {
					entry.setUnixMode(mode.getBits());
				} else {
					warnArchiveEntryModeIgnored(path);
				}
				entry.setSize(loader.getSize());
				out.putArchiveEntry(entry);
				loader.copyTo(out);
				out.closeArchiveEntry();
			}
		});
		fmts.put(Format.TAR
			public ArchiveOutputStream createArchiveOutputStream(OutputStream s) {
				return new TarArchiveOutputStream(s);
			}

			public void putEntry(String path
					ObjectLoader loader
					throws IOException {
				if (mode == FileMode.SYMLINK) {
							path
							loader.getCachedBytes(100)
					out.putArchiveEntry(entry);
					out.closeArchiveEntry();
					return;
				}

				final TarArchiveEntry entry = new TarArchiveEntry(path);
				if (mode == FileMode.REGULAR_FILE ||
				    mode == FileMode.EXECUTABLE_FILE)
					entry.setMode(mode.getBits());
				else
					warnArchiveEntryModeIgnored(path);
				entry.setSize(loader.getSize());
				out.putArchiveEntry(entry);
				loader.copyTo(out);
				out.closeArchiveEntry();
			}
		});
		formats = fmts;
	}
}
