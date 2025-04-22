package org.eclipse.jgit.treewalk;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;

public class FileTreeIteratorWithTimeControl extends FileTreeIterator {
	private TreeSet<Long> modTimes;

	public FileTreeIteratorWithTimeControl(FileTreeIterator p
			TreeSet<Long> modTimes) {
		super(p
		this.modTimes = modTimes;
	}

	public FileTreeIteratorWithTimeControl(FileTreeIterator p
			TreeSet<Long> modTimes) {
		super(p
		this.modTimes = modTimes;
	}

	public FileTreeIteratorWithTimeControl(Repository repo
			TreeSet<Long> modTimes) {
		super(repo);
		this.modTimes = modTimes;
	}

	public FileTreeIteratorWithTimeControl(File f
			TreeSet<Long> modTimes) {
		super(f
		this.modTimes = modTimes;
	}

	@Override
	public AbstractTreeIterator createSubtreeIterator(ObjectReader reader) {
		return new FileTreeIteratorWithTimeControl(this
				((FileEntry) current()).getFile()
	}

	@Override
	public long getEntryLastModified() {
		if (modTimes == null)
			return 0;
		Long cutOff = Long.valueOf(super.getEntryLastModified() + 1);
		SortedSet<Long> head = modTimes.headSet(cutOff);
		return head.isEmpty() ? 0 : head.last().longValue();
	}
}
