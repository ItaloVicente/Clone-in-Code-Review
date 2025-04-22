
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_ShowDiffTree")
class DiffTree extends TextBuiltin {
	@Option(name = "--recursive"
	private boolean recursive;

	@Argument(index = 0
	void tree_0(final AbstractTreeIterator c) {
		trees.add(c);
	}

	@Argument(index = 1
	private List<AbstractTreeIterator> trees = new ArrayList<>();

	@Option(name = "--"
	private TreeFilter pathFilter = TreeFilter.ALL;

	@Override
	protected void run() {
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.setRecursive(recursive);
			for (AbstractTreeIterator i : trees)
				walk.addTree(i);
			walk.setFilter(AndTreeFilter.create(TreeFilter.ANY_DIFF

			final int nTree = walk.getTreeCount();
			while (walk.next()) {
				for (int i = 1; i < nTree; i++) {
					outw.print(':');
				}
				for (int i = 0; i < nTree; i++) {
					final FileMode m = walk.getFileMode(i);
					final String s = m.toString();
					for (int pad = 6 - s.length(); pad > 0; pad--) {
						outw.print('0');
					}
					outw.print(s);
					outw.print(' ');
				}

				for (int i = 0; i < nTree; i++) {
					outw.print(walk.getObjectId(i).name());
					outw.print(' ');
				}

				char chg = 'M';
				if (nTree == 2) {
					final int m0 = walk.getRawMode(0);
					final int m1 = walk.getRawMode(1);
					if (m0 == 0 && m1 != 0) {
						chg = 'A';
					} else if (m0 != 0 && m1 == 0) {
						chg = 'D';
					} else if (m0 != m1 && walk.idEqual(0
						chg = 'T';
					}
				}
				outw.print(chg);

				outw.print('\t');
				outw.print(walk.getPathString());
				outw.println();
			}
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
