
package org.eclipse.jgit.pgm;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.ObjectDirectoryPackParser;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.PackParser;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_IndexPack")
class IndexPack extends TextBuiltin {
	@Option(name = "--fix-thin"
	private boolean fixThin;

	@Option(name = "--index-version"
	private int indexVersion = -1;

	@Override
	protected void run() {
		BufferedInputStream in = new BufferedInputStream(ins);
		try (ObjectInserter inserter = db.newObjectInserter()) {
			PackParser p = inserter.newPackParser(in);
			p.setAllowThin(fixThin);
			if (indexVersion != -1 && p instanceof ObjectDirectoryPackParser) {
				ObjectDirectoryPackParser imp = (ObjectDirectoryPackParser) p;
				imp.setIndexVersion(indexVersion);
			}
			p.parse(new TextProgressMonitor(errw));
			inserter.flush();
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
