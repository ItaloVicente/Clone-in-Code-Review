
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

class ReflogReaderImpl implements ReflogReader {
	private File logName;

	ReflogReaderImpl(Repository db
		logName = new File(db.getDirectory()
	}

	@Override
	public ReflogEntry getLastEntry() throws IOException {
		return getReverseEntry(0);
	}

	@Override
	public List<ReflogEntry> getReverseEntries() throws IOException {
		return getReverseEntries(Integer.MAX_VALUE);
	}

	@Override
	public ReflogEntry getReverseEntry(int number) throws IOException {
		if (number < 0)
			throw new IllegalArgumentException();

		final byte[] log;
		try {
			log = IO.readFully(logName);
		} catch (FileNotFoundException e) {
			if (logName.exists()) {
				throw e;
			}
			return null;
		}

		int rs = RawParseUtils.prevLF(log
		int current = 0;
		while (rs >= 0) {
			rs = RawParseUtils.prevLF(log
			if (number == current)
				return new ReflogEntryImpl(log
			current++;
		}
		return null;
	}

	@Override
	public List<ReflogEntry> getReverseEntries(int max) throws IOException {
		final byte[] log;
		try {
			log = IO.readFully(logName);
		} catch (FileNotFoundException e) {
			if (logName.exists()) {
				throw e;
			}
			return Collections.emptyList();
		}

		int rs = RawParseUtils.prevLF(log
		List<ReflogEntry> ret = new ArrayList<>();
		while (rs >= 0 && max-- > 0) {
			rs = RawParseUtils.prevLF(log
			ReflogEntry entry = new ReflogEntryImpl(log
			ret.add(entry);
		}
		return ret;
	}
}
