
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.storage.reftable.Reftable;

public class ReftableStack implements AutoCloseable {
	public static ReftableStack open(DfsReader ctx
			throws IOException {
		ReftableStack stack = new ReftableStack(files.size());
		boolean close = true;
		try {
			for (DfsReftable t : files) {
				stack.files.add(t);
				stack.tables.add(t.open(ctx));
			}
			close = false;
			return stack;
		} finally {
			if (close) {
				stack.close();
			}
		}
	}

	private final List<DfsReftable> files;
	private final List<Reftable> tables;

	private ReftableStack(int tableCnt) {
		this.files = new ArrayList<>(tableCnt);
		this.tables = new ArrayList<>(tableCnt);
	}

	public List<DfsReftable> files() {
		return Collections.unmodifiableList(files);
	}

	public List<Reftable> readers() {
		return Collections.unmodifiableList(tables);
	}

	@Override
	public void close() {
		for (Reftable t : tables) {
			try {
				t.close();
			} catch (IOException e) {
			}
		}
	}
}
