
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.lib.FileMode.TYPE_GITLINK;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class StageBuilder {
	private static final int SMALL_BATCH_SIZE = 5;

	private static final int TEMP_PARENTS = 128;

	private static final byte[] PEEL = { ' '

	private final String txnStage;
	private final String txnId;

	public StageBuilder(String txnStageNamespace
		this.txnStage = txnStageNamespace;
		this.txnId = txnId.name();
	}

	public List<ReceiveCommand> makeStageList(Repository git
			ObjectId newTree) throws IOException {
		try (RevWalk rw = new RevWalk(git);
				TreeWalk tw = new TreeWalk(rw.getObjectReader());
				ObjectInserter ins = git.newObjectInserter()) {
			if (AnyObjectId.equals(oldTree
				tw.addTree(new EmptyTreeIterator());
			} else {
				tw.addTree(rw.parseTree(oldTree));
			}
			tw.addTree(rw.parseTree(newTree));
			tw.setFilter(TreeFilter.ANY_DIFF);
			tw.setRecursive(true);

			Set<ObjectId> newObjs = new HashSet<>();
			while (tw.next()) {
				if (tw.getRawMode(1) == TYPE_GITLINK
						&& !tw.isPathSuffix(PEEL
					newObjs.add(tw.getObjectId(1));
				}
			}

			List<ReceiveCommand> cmds = makeStageList(newObjs
			ins.flush();
			return cmds;
		}
	}

	public List<ReceiveCommand> makeStageList(Set<ObjectId> newObjs
			@Nullable Repository git
					throws IOException {
		if (git == null || newObjs.size() <= SMALL_BATCH_SIZE) {
			List<ReceiveCommand> cmds = new ArrayList<>(newObjs.size());
			for (ObjectId id : newObjs) {
				stage(cmds
			}
			return cmds;
		}

		List<ReceiveCommand> cmds = new ArrayList<>();
		List<RevCommit> commits = new ArrayList<>();
		reduceObjects(cmds

		if (inserter == null || commits.size() <= 1
				|| (cmds.size() + commits.size()) <= SMALL_BATCH_SIZE) {
			for (RevCommit c : commits) {
				stage(cmds
			}
			return cmds;
		}

		ObjectId tip = null;
		for (int end = commits.size(); end > 0;) {
			int start = Math.max(0
			List<RevCommit> batch = commits.subList(start
			List<ObjectId> parents = new ArrayList<>(1 + batch.size());
			if (tip != null) {
				parents.add(tip);
			}
			parents.addAll(batch);

			CommitBuilder b = new CommitBuilder();
			b.setTreeId(batch.get(0).getTree());
			b.setParentIds(parents);
			b.setAuthor(tmpAuthor(batch));
			b.setCommitter(b.getAuthor());
			tip = inserter.insert(b);
			end = start;
		}
		stage(cmds
		return cmds;
	}

	private static PersonIdent tmpAuthor(List<RevCommit> commits) {
		int t = 0;
		for (int i = 0; i < commits.size();) {
			t = Math.max(t
		}
		return new PersonIdent(name
	}

	private void reduceObjects(List<ReceiveCommand> cmds
			List<RevCommit> commits
			Set<ObjectId> newObjs) throws IOException {
		try (RevWalk rw = new RevWalk(git)) {
			rw.setRetainBody(false);

			for (ObjectId id : newObjs) {
				RevObject obj = rw.parseAny(id);
				if (obj instanceof RevCommit) {
					rw.markStart((RevCommit) obj);
				} else {
					stage(cmds
				}
			}

			for (RevCommit c; (c = rw.next()) != null;) {
				commits.add(c);
				rw.markUninteresting(c);
			}
		}
	}

	private void stage(List<ReceiveCommand> cmds
		int estLen = txnStage.length() + txnId.length() + 5;
		StringBuilder n = new StringBuilder(estLen);
		n.append(txnStage).append(txnId).append('.');
		n.append(Integer.toHexString(cmds.size()));
		cmds.add(new ReceiveCommand(ObjectId.zeroId()
	}
}
