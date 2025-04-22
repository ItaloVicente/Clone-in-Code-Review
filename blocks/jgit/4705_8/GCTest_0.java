
package org.eclipse.jgit.pgm.debug;

import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.GC;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.TextBuiltin;

class Gc extends TextBuiltin {
	@Override
	protected void run() throws Exception {
		GC.gc(new TextProgressMonitor()
	}
}
