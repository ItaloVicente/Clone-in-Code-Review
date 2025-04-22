
package org.eclipse.jgit.pgm.debug;

import static java.lang.Integer.valueOf;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_ShowDirCache")
class ShowDirCache extends TextBuiltin {

	@Option(name = "--millis"
	private boolean millis = false;

	@Override
	protected void run() throws Exception {
		final SimpleDateFormat fmt;
		fmt = new SimpleDateFormat("yyyy-MM-dd

		final DirCache cache = db.readDirCache();
		for (int i = 0; i < cache.getEntryCount(); i++) {
			final DirCacheEntry ent = cache.getEntry(i);
			final FileMode mode = FileMode.fromBits(ent.getRawMode());
			final int len = ent.getLength();
			long lastModified = ent.getLastModified();
			final Date mtime = new Date(lastModified);
			final int stage = ent.getStage();

			outw.print(mode);
			outw.format(" %6d"
			outw.print(' ');
			if (millis)
				outw.print(lastModified);
			else
				outw.print(fmt.format(mtime));
			outw.print(' ');
			outw.print(ent.getObjectId().name());
			outw.print(' ');
			outw.print(stage);
			outw.print('\t');
			outw.print(ent.getPathString());
			outw.println();
		}
	}
}
