
package org.eclipse.jgit.pgm.debug;

import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.GC;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.TextBuiltin;

class Gc extends TextBuiltin {
	private static long TWO_WEEKS_MILLIS = 14L * 24L * 60L * 60L * 1000L;

	@Override
	protected void run() throws Exception {
		GC gc = new GC((FileRepository) db
				TWO_WEEKS_MILLIS);
		gc.gc();
	}
}
