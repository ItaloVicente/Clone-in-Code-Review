
package org.eclipse.jgit.subtree;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class SubtreeRevFilter extends RevFilter {

	private HashSet<RevCommit> subTrees;

	private Repository repo;

	private boolean includeBoundarySubtrees;

	private Map<String

	private SubtreeAnalyzer analyzer;

	public SubtreeRevFilter(Repository db) {
		this.repo = db;
		this.analyzer = new SubtreeAnalyzer(repo);
	}

	void setSplitters(Map<String
		this.splitters = splitters;
	}

	void setIncludeBoundarySubtrees(boolean include) {
		this.includeBoundarySubtrees = include;
		if (include) {
			if (subTrees == null) {
				subTrees = new HashSet<RevCommit>();
			}
		} else {
			subTrees = null;
		}
	}

	@Override
	public boolean include(RevWalk walker
			throws StopWalkException
			IncorrectObjectTypeException

		if (includeBoundarySubtrees && subTrees.contains(cmit)) {
			for (RevCommit parent : cmit.getParents()) {
				walker.markUninteresting(parent);
			}
		} else {

			Map<String
					cmit

			for (String subtreeName : subtreeParents.keySet()) {

				RevCommit subtreeCommit = walker.parseCommit(subtreeParents
						.get(subtreeName));

				if (includeBoundarySubtrees) {
					subTrees.add(subtreeCommit);
				} else {
					walker.markUninteresting(subtreeCommit);
				}

				if (splitters != null) {

					Config conf = SubtreeSplitter.loadSubtreeConfig(repo
					for (String name : conf
							.getSubsections(SubtreeSplitter.SUBTREE_SECTION)) {
						if (!splitters.containsKey(name)) {
							splitters.put(name
									name));
						}
					}

					for (SubtreeContext splitter : splitters.values()) {
						splitter.setSplitCommit(subtreeCommit
								SubtreeSplitter.NO_SUBTREE);
					}
					splitters.get(subtreeName).setSplitCommit(subtreeCommit
							subtreeCommit);
				}

			}
		}

		return true;
	}

	@Override
	public RevFilter clone() {
		return new SubtreeRevFilter(repo);
	}

	void flushCache(RevWalk walk) throws IOException {
		analyzer.flushCache(walk);
	}

	public void reset() {
		if (subTrees != null) {
			subTrees.clear();
		}
		splitters = null;
	}

	public void release() {
		analyzer.release();
	}

	public SubtreeAnalyzer getSubtreeAnalyzer() {
		return analyzer;
	}

}
