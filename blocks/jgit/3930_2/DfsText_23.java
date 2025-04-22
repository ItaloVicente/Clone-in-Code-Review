
package org.eclipse.jgit.storage.dfs;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.BaseRepositoryBuilder;

public abstract class DfsRepositoryBuilder<B extends DfsRepositoryBuilder
		extends BaseRepositoryBuilder<B
	private DfsReaderOptions readerOptions;

	public DfsReaderOptions getReaderOptions() {
		return readerOptions;
	}

	public B setReaderOptions(DfsReaderOptions opt) {
		readerOptions = opt;
		return self();
	}

	@Override
	public B setup() throws IllegalArgumentException
		super.setup();
		if (getReaderOptions() == null)
			setReaderOptions(new DfsReaderOptions());
		return self();
	}

	@SuppressWarnings("unchecked")
	@Override
	public abstract R build() throws IOException;


	@Override
	public B setGitDir(File gitDir) {
		if (gitDir != null)
			throw new IllegalArgumentException();
		return self();
	}

	@Override
	public B setObjectDirectory(File objectDirectory) {
		if (objectDirectory != null)
			throw new IllegalArgumentException();
		return self();
	}

	@Override
	public B addAlternateObjectDirectory(File other) {
		throw new UnsupportedOperationException("Alternates not supported");
	}

	@Override
	public B setWorkTree(File workTree) {
		if (workTree != null)
			throw new IllegalArgumentException();
		return self();
	}

	@Override
	public B setIndexFile(File indexFile) {
		if (indexFile != null)
			throw new IllegalArgumentException();
		return self();
	}
}
