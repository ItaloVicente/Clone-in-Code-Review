
package org.eclipse.jgit.pgm;

import java.lang.String;
import java.lang.System;
import java.text.MessageFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.pgm.CLIText;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.kohsuke.args4j.Argument;

@Command(common = true
class Archive extends TextBuiltin {
	@Argument(index = 0
	private AbstractTreeIterator tree;

	@Override
	protected void run() throws Exception {
		final TreeWalk walk = new TreeWalk(db);
		final ObjectReader reader = walk.getObjectReader();
		final MutableObjectId idBuf = new MutableObjectId();
		final ZipOutputStream out = new ZipOutputStream(outs);

		if (tree == null)
			throw die(CLIText.get().treeIsRequired);

		walk.reset();
		walk.addTree(tree);
		walk.setRecursive(true);
		while (walk.next()) {
			final String name = walk.getPathString();
			final FileMode mode = walk.getFileMode(0);

			if (mode == FileMode.TREE)
				continue;

			walk.getObjectId(idBuf
			final ZipEntry entry = new ZipEntry(name);
			final ObjectLoader loader = reader.open(idBuf);
			entry.setSize(loader.getSize());
			out.putNextEntry(entry);
			loader.copyTo(out);

			if (mode != FileMode.REGULAR_FILE)
						CLIText.get().archiveEntryModeIgnored
						name));
		}

		out.close();
	}
}
