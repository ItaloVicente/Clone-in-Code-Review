
package org.eclipse.jgit.pgm.debug;

import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.GC;

class Gc extends TextBuiltin {
	@Override
	protected void run() throws Exception {
		GC gc = new GC((FileRepository) db);
		gc.gc();
	}
}
