
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

class StartGenerator extends Generator {
	private final RevWalk walker;

	StartGenerator(RevWalk w) {
		walker = w;
	}

	@Override
	int outputType() {
		return 0;
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		Generator g;

		final RevWalk w = walker;
		RevFilter rf = w.getRevFilter();
		final TreeFilter tf = w.getTreeFilter();
		AbstractRevQueue q = walker.queue;

		if (rf == RevFilter.MERGE_BASE) {
			if (tf != TreeFilter.ALL)
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().cannotCombineTreeFilterWithRevFilter

			final MergeBaseGenerator mbg = new MergeBaseGenerator(w);
			walker.pending = mbg;
			walker.queue = AbstractRevQueue.EMPTY_QUEUE;
			mbg.init(q);
			return mbg.next();
		}

		final boolean uninteresting = q.anybodyHasFlag(RevWalk.UNINTERESTING);
		boolean boundary = walker.hasRevSort(RevSort.BOUNDARY);

		if (!boundary && walker instanceof ObjectWalk) {
			boundary = true;
		}
		if (boundary && !uninteresting) {
			boundary = false;
		}

		final DateRevQueue pending;
		int pendingOutputType = 0;
		if (q instanceof DateRevQueue)
			pending = (DateRevQueue)q;
		else
			pending = new DateRevQueue(q);
		if (tf != TreeFilter.ALL) {
			int rewriteFlag;
			if (w.getRewriteParents()) {
				pendingOutputType |= HAS_REWRITE | NEEDS_REWRITE;
				rewriteFlag = RevWalk.REWRITE;
			} else
				rewriteFlag = 0;
			rf = AndRevFilter.create(new TreeRevFilter(w
		}

		walker.queue = q;

		if (walker instanceof DepthWalk) {
			DepthWalk dw = (DepthWalk) walker;
			g = new DepthGenerator(dw
		} else {
			g = new PendingGenerator(w

			if (walker.hasRevSort(RevSort.BOUNDARY)) {
				((PendingGenerator) g).canDispose = false;
			}
		}

		if ((g.outputType() & NEEDS_REWRITE) != 0) {
			g = new FIFORevQueue(g);
			g = new RewriteGenerator(g);
		}

		if (walker.hasRevSort(RevSort.TOPO)
				&& (g.outputType() & SORT_TOPO) == 0)
			g = new TopoSortGenerator(g);
		if (walker.hasRevSort(RevSort.REVERSE))
			g = new LIFORevQueue(g);
		if (boundary)
			g = new BoundaryGenerator(w
		else if (uninteresting) {
			if (pending.peek() != null)
				g = new DelayRevQueue(g);
			g = new FixUninterestingGenerator(g);
		}

		w.pending = g;
		return g.next();
	}
}
