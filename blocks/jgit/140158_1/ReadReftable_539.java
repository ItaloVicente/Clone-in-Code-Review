
package org.eclipse.jgit.pgm.debug;

import static java.lang.Long.valueOf;

import java.text.MessageFormat;

import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;

@Command(usage = "usage_ReadDirCache")
class ReadDirCache extends TextBuiltin {
	@Override
	protected void run() throws Exception {
		final int cnt = 100;
		final long start = System.currentTimeMillis();
		for (int i = 0; i < cnt; i++)
			db.readDirCache();
		final long end = System.currentTimeMillis();
		outw.println(MessageFormat.format(CLIText.get().averageMSPerRead
				valueOf((end - start) / cnt)));
	}
}
