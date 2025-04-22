
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.opt.PathTreeFilterHandler;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

@Command(common = true
class Diff extends TextBuiltin {
	@Argument(index = 0
	void tree_0(final AbstractTreeIterator c) {
		trees.add(c);
	}

	@Argument(index = 1
	private final List<AbstractTreeIterator> trees = new ArrayList<AbstractTreeIterator>();

	@Option(name = "--"
	private TreeFilter pathFilter = TreeFilter.ALL;

	private DiffFormatter fmt = new DiffFormatter();

	@Override
	protected void run() throws Exception {
		final TreeWalk walk = new TreeWalk(db);
		walk.reset();
		walk.setRecursive(true);
		for (final AbstractTreeIterator i : trees)
			walk.addTree(i);
		walk.setFilter(AndTreeFilter.create(TreeFilter.ANY_DIFF

		while (walk.next())
			outputDiff(System.out
				walk.getObjectId(0)
				walk.getObjectId(1)
	}

	protected void outputDiff(PrintStream out
			ObjectId id1
		String name1 = "a/" + path;
		String name2 =  "b/" + path;
		out.println("diff --git " + name1 + " " + name2);
		boolean isNew=false;
		boolean isDelete=false;
		if (id1.equals(ObjectId.zeroId())) {
			out.println("new file mode " + mode2);
			isNew=true;
		} else if (id2.equals(ObjectId.zeroId())) {
			out.println("deleted file mode " + mode1);
			isDelete=true;
		} else if (!mode1.equals(mode2)) {
			out.println("old mode " + mode1);
			out.println("new mode " + mode2);
		}
		out.println("index " + id1.abbreviate(db
			+ ".." + id2.abbreviate(db
			+ (mode1.equals(mode2) ? " " + mode1 : ""));
		out.println("--- " + (isNew ?  "/dev/null" : name1));
		out.println("+++ " + (isDelete ?  "/dev/null" : name2));
		RawText a = getRawText(id1);
		RawText b = getRawText(id2);
		MyersDiff diff = new MyersDiff(a
		fmt.formatEdits(out
	}

	private RawText getRawText(ObjectId id) throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] { });
		return new RawText(db.openBlob(id).getCachedBytes());
	}
}

