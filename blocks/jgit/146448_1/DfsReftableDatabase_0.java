
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.reftable.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

public class DfsReftableBatchRefUpdate extends ReftableBatchRefUpdate {
	private static final int AVG_BYTES = 36;

	private final DfsReftableDatabase refdb;
	private final DfsObjDatabase odb;
	private final ReftableConfig reftableConfig;

	protected DfsReftableBatchRefUpdate(DfsReftableDatabase refdb
										DfsObjDatabase odb) {
		super(refdb
				() -> refdb.stack().readers());
		this.refdb = refdb;
		this.odb = odb;
		reftableConfig = refdb.getReftableConfig();
	}

	@Override
	protected void applyUpdates(RevWalk rw
			throws IOException {
		List<Ref> newRefs = toNewRefs(rw
		long updateIndex = nextUpdateIndex();
		Set<DfsPackDescription> prune = Collections.emptySet();
		DfsPackDescription pack = odb.newPack(PackSource.INSERT);
		try (DfsOutputStream out = odb.writeFile(pack
			ReftableConfig cfg = DfsPackCompactor
					.configureReftable(reftableConfig

			ReftableWriter.Stats stats;
			if (refdb.compactDuringCommit()
					&& newRefs.size() * AVG_BYTES <= cfg.getRefBlockSize()
					&& canCompactTopOfStack(cfg)) {
				ByteArrayOutputStream tmp = new ByteArrayOutputStream();
				write(tmp
				stats = compactTopOfStack(out
				prune = toPruneTopOfStack();
			} else {
				stats = write(out
			}
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(stats);
		}

		odb.commitPack(Collections.singleton(pack)
		odb.addReftable(pack
		refdb.clearCache();
	}

	private boolean canCompactTopOfStack(ReftableConfig cfg)
			throws IOException {
		ReftableStack stack = refdb.stack();
		List<Reftable> readers = stack.readers();
		if (readers.isEmpty()) {
			return false;
		}

		int lastIdx = readers.size() - 1;
		DfsReftable last = stack.files().get(lastIdx);
		DfsPackDescription desc = last.getPackDescription();
		if (desc.getPackSource() != PackSource.INSERT
				|| !packOnlyContainsReftable(desc)) {
			return false;
		}

		Reftable table = readers.get(lastIdx);
		int bs = cfg.getRefBlockSize();
		return table instanceof ReftableReader
				&& ((ReftableReader) table).size() <= 3 * bs;
	}

	private ReftableWriter.Stats compactTopOfStack(OutputStream out
			ReftableConfig cfg
		List<Reftable> stack = refdb.stack().readers();
		Reftable last = stack.get(stack.size() - 1);

		List<Reftable> tables = new ArrayList<>(2);
		tables.add(last);
		tables.add(new ReftableReader(BlockSource.from(newTable)));

		ReftableCompactor compactor = new ReftableCompactor();
		compactor.setConfig(cfg);
		compactor.setIncludeDeletes(true);
		compactor.addAll(tables);
		compactor.compact(out);
		return compactor.getStats();
	}

	private Set<DfsPackDescription> toPruneTopOfStack() throws IOException {
		List<DfsReftable> stack = refdb.stack().files();
		DfsReftable last = stack.get(stack.size() - 1);
		return Collections.singleton(last.getPackDescription());
	}

	private boolean packOnlyContainsReftable(DfsPackDescription desc) {
		for (PackExt ext : PackExt.values()) {
			if (ext != REFTABLE && desc.hasFileExt(ext)) {
				return false;
			}
		}
		return true;
	}
}
