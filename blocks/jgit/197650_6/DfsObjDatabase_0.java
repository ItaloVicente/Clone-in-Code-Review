package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.pack.PackExt.COMMIT_GRAPH;

public class DfsCommitGraph extends BlockBasedFile {
	public DfsCommitGraph(DfsPackDescription desc) {
		this(DfsBlockCache.getInstance()
	}

	public DfsCommitGraph(DfsBlockCache cache
		super(cache

		int bs = desc.getBlockSize(COMMIT_GRAPH);
		if (bs > 0) {
			setBlockSize(bs);
		}

		long sz = desc.getFileSize(COMMIT_GRAPH);
		length = sz > 0 ? sz : -1;
	}

	public DfsPackDescription getPackDescription() {
		return desc;
	}

}
