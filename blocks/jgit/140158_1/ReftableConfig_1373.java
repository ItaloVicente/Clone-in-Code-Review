
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.storage.reftable.ReftableWriter.Stats;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ReflogEntry;

public class ReftableCompactor {
	private final ReftableWriter writer = new ReftableWriter();
	private final ArrayDeque<Reftable> tables = new ArrayDeque<>();

	private long compactBytesLimit;
	private long bytesToCompact;
	private boolean includeDeletes;
	private long minUpdateIndex = -1;
	private long maxUpdateIndex;
	private long oldestReflogTimeMillis;
	private Stats stats;

	public ReftableCompactor setConfig(ReftableConfig cfg) {
		writer.setConfig(cfg);
		return this;
	}

	public ReftableCompactor setCompactBytesLimit(long bytes) {
		compactBytesLimit = bytes;
		return this;
	}

	public ReftableCompactor setIncludeDeletes(boolean deletes) {
		includeDeletes = deletes;
		return this;
	}

	public ReftableCompactor setMinUpdateIndex(long min) {
		minUpdateIndex = min;
		return this;
	}

	public ReftableCompactor setMaxUpdateIndex(long max) {
		maxUpdateIndex = max;
		return this;
	}

	public ReftableCompactor setOldestReflogTimeMillis(long timeMillis) {
		oldestReflogTimeMillis = timeMillis;
		return this;
	}

	public void addAll(List<? extends Reftable> readers) throws IOException {
		tables.addAll(readers);
		for (Reftable r : readers) {
			if (r instanceof ReftableReader) {
				adjustUpdateIndexes((ReftableReader) r);
			}
		}
	}

	public boolean tryAddFirst(ReftableReader reader) throws IOException {
		long sz = reader.size();
		if (compactBytesLimit > 0 && bytesToCompact + sz > compactBytesLimit) {
			return false;
		}
		bytesToCompact += sz;
		adjustUpdateIndexes(reader);
		tables.addFirst(reader);
		return true;
	}

	private void adjustUpdateIndexes(ReftableReader reader) throws IOException {
		if (minUpdateIndex == -1) {
			minUpdateIndex = reader.minUpdateIndex();
		} else {
			minUpdateIndex = Math.min(minUpdateIndex
		}
		maxUpdateIndex = Math.max(maxUpdateIndex
	}

	public void compact(OutputStream out) throws IOException {
		MergedReftable mr = new MergedReftable(new ArrayList<>(tables));
		mr.setIncludeDeletes(includeDeletes);

		writer.setMinUpdateIndex(Math.max(minUpdateIndex
		writer.setMaxUpdateIndex(maxUpdateIndex);
		writer.begin(out);
		mergeRefs(mr);
		mergeLogs(mr);
		writer.finish();
		stats = writer.getStats();
	}

	public Stats getStats() {
		return stats;
	}

	private void mergeRefs(MergedReftable mr) throws IOException {
		try (RefCursor rc = mr.allRefs()) {
			while (rc.next()) {
				writer.writeRef(rc.getRef()
			}
		}
	}

	private void mergeLogs(MergedReftable mr) throws IOException {
		if (oldestReflogTimeMillis == Long.MAX_VALUE) {
			return;
		}

		try (LogCursor lc = mr.allLogs()) {
			while (lc.next()) {
				long updateIndex = lc.getUpdateIndex();
				if (updateIndex < minUpdateIndex
						|| updateIndex > maxUpdateIndex) {
					continue;
				}

				String refName = lc.getRefName();
				ReflogEntry log = lc.getReflogEntry();
				if (log == null) {
					if (includeDeletes) {
						writer.deleteLog(refName
					}
					continue;
				}

				PersonIdent who = log.getWho();
				if (who.getWhen().getTime() >= oldestReflogTimeMillis) {
					writer.writeLog(
							refName
							updateIndex
							who
							log.getOldId()
							log.getNewId()
							log.getComment());
				}
			}
		}
	}
}
