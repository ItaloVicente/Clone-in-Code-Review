package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.storage.pack.CachedPack;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.BundleWriter;

public class DfsBundleWriter {
	public static void writeEntireRepositoryAsBundle(ProgressMonitor pm
			OutputStream os
		BundleWriter bw = new BundleWriter(db);
		db.getRefDatabase().getRefs().forEach(bw::include);
		List<CachedPack> packs = new ArrayList<>();
		for (DfsPackFile p : db.getObjectDatabase().getPacks()) {
			packs.add(new DfsCachedPack(p));
		}
		bw.addObjectsAsIs(packs);
		bw.writeBundle(pm
	}

	private DfsBundleWriter() {
	}
}
