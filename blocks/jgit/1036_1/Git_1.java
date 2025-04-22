package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

public class AddCommand extends GitCommand<DirCache> {

	private String filepath;

	public AddCommand(Repository repo
		super(repo);
		this.filepath = filepath;
	}

	public DirCache call() throws IOException {
		final File file = new File(repo.getWorkDir()
		if (!file.exists()) {
			DirCache dc = DirCache.lock(repo);
			try {
				if (dc.findEntry(filepath) < 0) {
					throw new IllegalStateException(MessageFormat.format(
							"File {0} does not exist"
				} else {
					return dc;
				}
			} finally {
				dc.unlock();
			}
		}

		if (file.isDirectory())
			throw new IllegalStateException(MessageFormat.format(
					"Adding directory {0} not yet supported"

		ObjectWriter ow = new ObjectWriter(repo);
		final ObjectId id = ow.writeBlob(file);

		DirCache dc = DirCache.lock(repo);

		try {
			DirCacheBuilder builder = dc.builder();
			final TreeWalk tw = new TreeWalk(repo);
			tw.reset();
			tw.addTree(new DirCacheBuildIterator(builder));
			FileTreeIterator fileTreeIterator = new FileTreeIterator(
					repo.getWorkDir()
			tw.addTree(fileTreeIterator);
			tw.setRecursive(true);

			String lastAddedFile = null;

			while (tw.next()) {
				if (tw.getPathString().equals(filepath)) {
					if (!(filepath.equals(lastAddedFile))) {
						DirCacheEntry entry = new DirCacheEntry(filepath);
						entry.setObjectId(id);
						entry.setFileMode(repo.getFS().canExecute(file) ? FileMode.EXECUTABLE_FILE
								: FileMode.REGULAR_FILE);
						entry.setLastModified(file.lastModified());
						entry.setLength((int) file.length());

						builder.add(entry);
						lastAddedFile = filepath;
					}
				} else {
					final DirCacheIterator c = tw.getTree(0
							DirCacheIterator.class);
					builder.add(c.getDirCacheEntry());
				}
			}

			builder.commit();
		} finally {
			dc.unlock();
		}

		return dc;
	}

}
