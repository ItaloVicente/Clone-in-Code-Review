package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class AddCommand extends GitCommand<DirCache> {

	private Collection<String> filepatterns;

	public AddCommand(Repository repo) {
		super(repo);
		filepatterns = new LinkedList<String>();
	}

	public AddCommand addFilepattern(String filepattern) {
		checkCallable();
		filepatterns.add(filepattern);
		return this;
	}

	public DirCache call() throws NoFilepatternException {

		if (filepatterns.isEmpty())
			throw new NoFilepatternException(JGitText.get().atLeastOnePatternIsRequired);
		checkCallable();
		DirCache dc = null;

		try {
			dc = DirCache.lock(repo);
			ObjectWriter ow = new ObjectWriter(repo);
			DirCacheIterator c;

			DirCacheBuilder builder = dc.builder();
			final TreeWalk tw = new TreeWalk(repo);
			tw.reset();
			tw.addTree(new DirCacheBuildIterator(builder));
			FileTreeIterator fileTreeIterator = new FileTreeIterator(
					repo.getWorkDir()
			tw.addTree(fileTreeIterator);
			tw.setRecursive(true);
			tw.setFilter(PathFilterGroup.createFromStrings(filepatterns));

			String lastAddedFile = null;

			while (tw.next()) {
				String path = tw.getPathString();

				final File file = new File(repo.getWorkDir()
				if (!(path.equals(lastAddedFile))) {
					 FileTreeIterator f = tw.getTree(1
						DirCacheEntry entry = new DirCacheEntry(path);
						entry.setLength((int)f.getEntryLength());
						entry.setLastModified(f.getEntryLastModified());
						entry.setFileMode(f.getEntryFileMode());
						entry.setObjectId(ow.writeBlob(file));

						builder.add(entry);
						lastAddedFile = path;
					} else {
						c = tw.getTree(0
						builder.add(c.getDirCacheEntry());
					}
				}
			}
			builder.commit();
			setCallable(false);
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfAddCommand
		} finally {
			if (dc != null)
				dc.unlock();
		}

		return dc;
	}

}
