
package org.eclipse.jgit.storage.pack;

import java.util.concurrent.Callable;

import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;

final class DeltaTask implements Callable<Object> {
	private final PackConfig config;

	private final ObjectReader templateReader;

	private final DeltaCache dc;

	private final ProgressMonitor pm;

	private final int batchSize;

	private final int start;

	private final ObjectToPack[] list;

	DeltaTask(PackConfig config
			ProgressMonitor pm
		this.config = config;
		this.templateReader = reader;
		this.dc = dc;
		this.pm = pm;
		this.batchSize = batchSize;
		this.start = start;
		this.list = list;
	}

	public Object call() throws Exception {
		final ObjectReader or = templateReader.newReader();
		try {
			DeltaWindow dw;
			dw = new DeltaWindow(config
			dw.search(pm
		} finally {
			or.release();
		}
		return null;
	}
}
