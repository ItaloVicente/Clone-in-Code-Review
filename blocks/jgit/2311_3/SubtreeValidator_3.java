
package org.eclipse.jgit.subtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

class SubtreeValidator {

	protected abstract class ModelCommit<T extends ModelCommit> {

		protected RevCommit source;

		protected RevCommit result;

		protected String name;

		protected String[] parents;

		protected HashMap<String

		@Override
		public String toString() {
			return name;
		}

		@SuppressWarnings("unchecked")
		protected T setParents(String... parents) {
			this.parents = parents;
			return (T) this;
		}

		@SuppressWarnings("unchecked")
		protected T addSubtree(String id
			subtrees.put(id
			return (T) this;
		}

		protected void setName(String name) {
			this.name = name;
		}

		@Override
		protected abstract ModelCommit clone()
				throws CloneNotSupportedException;

		@SuppressWarnings("unchecked")
		protected T copy(ModelCommit<T> mc) {
			name = mc.name;
			parents = Arrays.copyOf(mc.parents
			subtrees = new HashMap<String
			return (T) this;
		}

	}

	protected class RewrittenCommit extends ModelCommit<RewrittenCommit> {

		protected String sourceName;

		protected RewrittenCommit(String sourceName
			this.sourceName = sourceName;
			this.source = source;
		}

		@Override
		protected RewrittenCommit clone() throws CloneNotSupportedException {
			return new RewrittenCommit(sourceName
					.copy(this);
		}

		@Override
		protected RewrittenCommit addSubtree(String id
			throw new RuntimeException("Add subtrees to source commit");
		}

	}

	protected class NormalCommit extends ModelCommit<NormalCommit> {
		protected boolean rewrite;

		protected NormalCommit(RevCommit commit) {
			this.source = commit;
			this.result = commit;
		}

		protected NormalCommit setRewrite(boolean rewrite) {
			this.rewrite = rewrite;
			return this;
		}

		@Override
		protected NormalCommit clone() throws CloneNotSupportedException {
			return new NormalCommit(result).setRewrite(rewrite).copy(this);
		}

	}

	protected class SubtreeCommit extends ModelCommit<SubtreeCommit> {
		protected SubtreeCommit(RevCommit commit) {
			this.source = commit;
			this.result = commit;
		}

		@Override
		protected SubtreeCommit clone() throws CloneNotSupportedException {
			return new SubtreeCommit(result).copy(this);
		}

	}

	protected class SplitCommit extends ModelCommit<SplitCommit> {
		protected String subtree;

		protected SplitCommit(String subtree
			this.subtree = subtree;
			this.source = source;
		}

		@Override
		protected SplitCommit clone() throws CloneNotSupportedException {
			return new SplitCommit(subtree
		}

	}

	private TreeMap<String

	private RevCommit start;

	private Repository db;

	private RevWalk rw;

	private String[] subtreePaths = new String[0];

	protected SubtreeValidator(Repository db
		this.db = db;
		this.rw = rw;
	}

	protected SubtreeValidator setSplitPaths(String... paths) {
		subtreePaths = paths;
		return this;
	}

	private <T extends ModelCommit> T add(String name
		if (commits.containsKey(name)) {
			throw new IllegalArgumentException("Commit " + name
					+ " already exists");
		}
		mc.setName(name);
		commits.put(name
		return mc;
	}

	protected SubtreeCommit subtree(String name
		return add(name
	}

	protected SplitCommit split(String name
		return add(name
	}

	protected NormalCommit normal(String name
		return add(name
	}

	protected RewrittenCommit rewritten(String name
		if (!(commits.get(parentName) instanceof NormalCommit)) {
			throw new IllegalArgumentException("Parent is not a normal commit");
		}
		NormalCommit parent = (NormalCommit) commits.get(parentName);
		parent.rewrite = true;
		return add(name
	}

	protected SubtreeValidator setStart(RevCommit start) {
		this.start = start;
		return this;
	}

	private void validateParentList(ModelCommit mc)
			throws MissingObjectException
			IncorrectObjectTypeException
		rw.parseCommit(mc.result);
		assertNotNull("Parents not set for " + mc.name
		assertEquals("Parent length of " + mc.name
				mc.result.getParentCount());
		List<RevCommit> parentList = Arrays.asList(mc.result.getParents());
		for (String p : mc.parents) {
			ModelCommit pmc = commits.get(p);
			assertNotNull("Info not provided for parent " + p + " of commit "
					+ mc.name
			assertTrue("Parents of " + mc.name + " do not contain " + pmc.name
					parentList.contains(pmc.result));
		}
	}

	private void validateSubtrees(SubtreeSplitter ss

		Map<String
		if (mc instanceof RewrittenCommit) {
			RewrittenCommit rc = (RewrittenCommit) mc;
			NormalCommit nc = (NormalCommit) commits.get(rc.sourceName);
			subtrees = nc.subtrees;
		} else if (mc instanceof NormalCommit) {
			NormalCommit nc = (NormalCommit) mc;
			subtrees = nc.subtrees;
		} else {
			return;
		}

		for (SubtreeContext sc : ss.getSubtreeContexts().values()) {
			String resultName = subtrees.get(sc.id);
			ModelCommit resultMc = resultName != null ? commits.get(resultName) : null;
			RevCommit result = resultMc != null ? resultMc.result : null;
			RevCommit split = sc.getSplitCommit(mc.source);
			split = split == SubtreeContext.NO_SUBTREE ? null : split;
			assertEquals("Subtree " + sc.id + " of " + mc.name
		}

		for (Object id : subtrees.keySet()) {
			boolean found = false;
			for (SubtreeContext sc : ss.getSubtreeContexts().values()) {
				if (sc.getId().equals(id)) {
					found = true;
					break;
				}
			}

			assertTrue("Subtree id " + id + " can not be found on " + mc.name
					found);
		}
	}

	private boolean compareTree(ObjectId t1
			throws MissingObjectException
			CorruptObjectException
		TreeWalk tw = new TreeWalk(db);
		try {
			tw.addTree(t1);
			tw.addTree(t2);
			tw.setFilter(TreeFilter.ANY_DIFF);
			while (tw.next()) {
				if (!tw.getPathString().equals(".gitsubtree")) {
					return false;
				}
			}
			return true;
		} finally {
			tw.release();
		}
	}

	protected void validate() throws IOException {

		SubtreeSplitter ss = new SubtreeSplitter(db
		ArrayList<PathBasedContext> pbc = new ArrayList<PathBasedContext>();
		for (String subtreePath : subtreePaths) {
			pbc.add(new PathBasedContext(subtreePath
		}
		ss.splitSubtrees(start
		Collection<SubtreeContext> contexts = ss.getSubtreeContexts().values();
		Map<ObjectId

		for (Ref ref : db.getRefDatabase().getRefs("refs/tags").values()) {
			db.getRefDatabase().newUpdate(ref.getName()
		}

		for (String name : commits.keySet()) {
			ModelCommit mc = commits.get(name);
			if (mc instanceof SplitCommit) {
				SplitCommit sc = (SplitCommit) mc;
				for (SubtreeContext candidate : contexts) {
					if (candidate.getId().equals(sc.subtree)) {
						sc.result = candidate.getSplitCommit(sc.source);
						break;
					}
				}
			} else if (mc instanceof RewrittenCommit) {
				RewrittenCommit rc = (RewrittenCommit) mc;
				rc.result = rewritten.get(rc.source);
			}

			assertNotNull("Couldn't find commit for " + mc.name

			rw.parseCommit(mc.result);
			TagBuilder newTag = new TagBuilder();
			newTag.setTag(mc.name);
			newTag.setMessage(mc.name);
			newTag.setObjectId(mc.result);
			ObjectInserter oi = db.newObjectInserter();
			oi.insert(newTag);
			oi.flush();
			oi.release();
			RefUpdate tagRef = db.updateRef("refs/tags/" + mc.name);
			tagRef.setNewObjectId(rw.parseAny(newTag.getObjectId()));
			tagRef.setForceUpdate(true);
			tagRef.update(rw);
		}

		for (String name : commits.keySet()) {
			ModelCommit mc = commits.get(name);
			validateParentList(mc);
			validateSubtrees(ss
			if (mc instanceof SplitCommit) {
				SplitCommit sc = (SplitCommit) mc;
				TreeWalk tw = TreeWalk.forPath(db
						sc.source.getTree());
				assertEquals(sc.result.getTree()
				tw.release();
				assertNull(rewritten.get(sc.result));
				assertNotNull(sc.result);
				assertNotSame(sc.source
			} else if (mc instanceof RewrittenCommit) {
				RewrittenCommit rc = (RewrittenCommit) mc;
				assertNotNull(rc.result);
				assertTrue("Tree of rewritten commit " + rc.name
						+ " does not match source commit"
						compareTree(rc.source.getTree()
				assertNotSame(rc.source
			} else if (mc instanceof NormalCommit) {
				NormalCommit nc = (NormalCommit) mc;
				assertNotNull(
						nc.name
								+ " should be in rewritten map since it's a mainline commit"
						rewritten.get(nc.result));
				if (!nc.rewrite) {
					assertEquals(nc.name + " should not be rewritten"
							nc.result
				}
			} else if (mc instanceof SubtreeCommit) {
				SubtreeCommit sc = (SubtreeCommit) mc;
				assertNull(
						sc.name + " is a subtree and sould not be rewritten"
						rewritten.get(sc.result));
			}
		}
	}

	protected void reset() {
		for (String name : commits.keySet()) {
			ModelCommit mc = commits.get(name);
			if (mc instanceof SplitCommit) {
				((SplitCommit) mc).result = null;
			} else if (mc instanceof RewrittenCommit) {
				((RewrittenCommit) mc).result = null;
			}
		}
		start = null;
	}

	@Override
	protected SubtreeValidator clone() throws CloneNotSupportedException {
		SubtreeValidator clone = new SubtreeValidator(db
				.setSplitPaths(subtreePaths);
		for (String name : commits.keySet()) {
			ModelCommit<?> mc = commits.get(name).clone();
			clone.commits.put(name
		}
		return clone;
	}

}
