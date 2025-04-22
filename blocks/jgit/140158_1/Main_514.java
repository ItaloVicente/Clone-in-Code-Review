
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.QuotedString;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StopOptionHandler;

@Command(common = true
class LsTree extends TextBuiltin {
	@Option(name = "--recursive"
	private boolean recursive;

	@Argument(index = 0
	private AbstractTreeIterator tree;

	@Argument(index = 1)
	@Option(name = "--"
	private List<String> paths = new ArrayList<>();

	@Override
	protected void run() {
		try (TreeWalk walk = new TreeWalk(db)) {
			if (paths.size() > 0) {
				walk.setFilter(PathFilterGroup.createFromStrings(paths));
			}
			walk.setRecursive(recursive);
			walk.addTree(tree);

			while (walk.next()) {
				final FileMode mode = walk.getFileMode(0);
				if (mode == FileMode.TREE) {
					outw.print('0');
				}
				outw.print(mode);
				outw.print(' ');
				outw.print(Constants.typeString(mode.getObjectType()));

				outw.print(' ');
				outw.print(walk.getObjectId(0).name());

				outw.print('\t');
				outw.print(QuotedString.GIT_PATH.quote(walk.getPathString()));
				outw.println();
			}
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
