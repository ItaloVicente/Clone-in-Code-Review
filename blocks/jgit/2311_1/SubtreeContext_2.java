
package org.eclipse.jgit.subtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

class SubtreeAnalyzer {

	private static final class AllSameFilter extends TreeFilter {
		private static final int baseTree = 0;

		@Override
		public boolean include(TreeWalk walker) throws MissingObjectException
				IncorrectObjectTypeException
			final int n = walker.getTreeCount();
			if (n == 1)
				return true;

			boolean allSame = true;
			boolean allTrees = true;
			final int m = walker.getRawMode(baseTree);
			for (int i = 1; i < n; i++) {
				if (walker.getRawMode(i) != FileMode.TYPE_TREE) {
					allTrees = false;
				}

				if (walker.getRawMode(i) != m || !walker.idEqual(i
					allSame = false;
				}
			}

			return (walker.isRecursive() && allTrees) ? true : allSame;
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
			return "ALL_SAME";
		}

	}

	private static final String NOTES_REF = "refs/notes/subtrees";

	private static final TreeFilter ALL_SAME_TREE_FILTER = new AllSameFilter();

	private TreeWalk tw;

	private Repository repo;

	private Map<RevCommit

	private NoteMap noteMap;

	private ObjectReader reader;

	private ObjectId notesCommitId;

	private ObjectInserter inserter;

	SubtreeAnalyzer(Repository db) {
		repo = db;
		reader = repo.newObjectReader();
		inserter = repo.newObjectInserter();
		tw = new TreeWalk(reader);
	}

	void release() {
		tw.release();
		reader.release();
		inserter.release();
	}

	Map<String
			RevWalk walker) throws IOException {
		Map<String

		subtreeParents = subtreeCache.get(cmit);

		if (subtreeParents == null) {
			subtreeParents = parseNote(cmit
			if (subtreeParents == null) {
				if (loadNoteMap(walker)) {
					subtreeParents = parseNote(cmit
				}
			}
		}

		if (subtreeParents == null) {
			subtreeParents = analyzeSubtrees(cmit
		}

		subtreeCache.put(cmit

		return subtreeParents;
	}

	private HashMap<String
			RevWalk walker) throws IOException {
		HashMap<String
		Config cfg = SubtreeSplitter.loadSubtreeConfig(repo
		if (cfg != null) {

			for (RevCommit parent : cmit.getParents()) {
				walker.parseBody(parent);

				int bestScore = scoreTrees(parent.getTree()
				String bestId = null;

				Set<String> subtreeIds = cfg
						.getSubsections(SubtreeSplitter.SUBTREE_SECTION);
				for (String curId : subtreeIds) {

					String curPath = cfg.getString(
							SubtreeSplitter.SUBTREE_SECTION
							SubtreeSplitter.SUBTREE_PATH_PROP);

					if (curPath == null) {
						continue;
					}

					ObjectId subtree = TreeWalk.findObject(cmit.getTree()
							curPath

					if (subtree == null) {
						continue;
					}

					int curScore = scoreTrees(subtree
					if (curScore > bestScore) {
						bestScore = curScore;
						bestId = curId;
					}
				}

				if (bestId != null) {
					subtreeParents.put(bestId
				}
			}

		}
		return subtreeParents;
	}

	private int scoreTrees(ObjectId tree1
		tw.reset();
		tw.setRecursive(true);
		tw.setFilter(ALL_SAME_TREE_FILTER);
		tw.addTree(tree1);
		tw.addTree(tree2);

		int score = 0;
		while (tw.next()) {
			if (tw.getFileMode(0) != FileMode.TREE) {
				score++;
			}
		}
		return score;
	}

	private HashMap<String
			throws MissingObjectException
		if (noteMap != null) {
			ObjectId note = noteMap.get(cmit);
			if (note != null) {
				RevObject object = walker.parseAny(note);
				if (object instanceof RevBlob) {
					RevBlob noteBlob = (RevBlob) object;
					ObjectLoader loader = reader.open(noteBlob.getId()
							Constants.OBJ_BLOB);
					String footers = new String(loader.getBytes());

					String footerKey = SubtreeSplitter.SUBTREE_FOOTER_KEY
							.getName();
					int footerLen = footerKey.length();

					StringReader sr = new StringReader(footers.trim());
					BufferedReader br = new BufferedReader(sr);
					ArrayList<String> lines = new ArrayList<String>();
					String line = null;
					while ((line = br.readLine()) != null) {

						if (line.startsWith(footerKey)) {

							int idx = footerLen;
							while (line.charAt(idx) != ':') {
								idx++;
							}
							idx++;
							if (idx < line.length()) {
								lines.add(line.substring(idx).trim());
							}
						}
					}

					return parseSubtreeFooters(repo
				}
			}
		}

		return null;
	}

	private boolean loadNoteMap(RevWalk walker) throws IOException {
		ObjectId id = repo.resolve(NOTES_REF);
		if (id != null && !id.equals(notesCommitId)) {
			RevCommit noteCommit = walker.parseCommit(id);
			if (noteCommit != null) {
				notesCommitId = id;
				noteMap = NoteMap.read(reader
				return true;
			}
		}
		return false;
	}

	private HashMap<String
			RevCommit commit
			throws IOException {

		HashMap<String

		Config config = SubtreeSplitter.loadSubtreeConfig(db
		RevCommit[] parents = commit.getParents();

		for (String line : footerLines) {
			int len = line.length();
			int idx = 0;

			while (idx < len && Character.isWhitespace(line.charAt(idx))) {
				idx++;
			}
			int shaStart = idx;
			while (idx < len && !Character.isWhitespace(line.charAt(idx))) {
				idx++;
			}

			ObjectId commitId = null;
			String sha1 = line.substring(shaStart
			try {
				commitId = ObjectId.fromString(sha1);
			} catch (IllegalArgumentException iae) {
				throw new SubtreeFooterException("Can't parse ObjectId: "
						+ sha1 + " in footer of " + commit.name());
			}

			RevCommit parentRevCommit = null;
			for (RevCommit parent : parents) {
				if (parent.equals(commitId)) {
					parentRevCommit = parent;
					break;
				}
			}
			if (parentRevCommit == null) {
				throw new SubtreeFooterException("Sub-Tree \""
						+ commitId.name() + "\" is not a parent of \""
						+ commit.name() + "\"");
			}

			while (idx < len && Character.isWhitespace(line.charAt(idx))) {
				idx++;
			}

			String subtreeId = line.substring(idx).trim();
			if (subtreeId.isEmpty()) {
				throw new SubtreeFooterException("Sub-Tree id not specified \""
						+ line + "\" in footer of " + commit.name());
			}

			if (config == null
					|| !config.getSubsections("subtree").contains(subtreeId)) {
				throw new SubtreeFooterException("Sub-Tree \"" + subtreeId
						+ "\" does not exist in .gitsubtree file for commit "
						+ commit.name());
			}

			result.put(subtreeId
		}

		return result;
	}

	ObjectId flushCache(RevWalk walker) throws IOException {

		for (int retryCount = 0; retryCount < 3; retryCount++) {

			loadNoteMap(walker);
			boolean madeUpdate = false;
			for (RevCommit cmit : subtreeCache.keySet()) {
				Map<String
				if (updateNoteMap(cmit
					madeUpdate = true;
				}
			}

			if (!madeUpdate) {
				return notesCommitId;
			}

			CommitBuilder cb = new CommitBuilder();
			cb.setTreeId(noteMap.writeTree(inserter));
			cb.setAuthor(new PersonIdent("Subtree Splitter"
			cb.setCommitter(new PersonIdent("Subtree Splitter"
			cb.setMessage("subtree cache");
			ObjectId commitId = inserter.insert(cb);

			RefUpdate update = repo.updateRef(NOTES_REF);
			update.setForceUpdate(true);
			update.setExpectedOldObjectId(notesCommitId);
			update.setNewObjectId(commitId);

			switch (update.update()) {
			case FORCED:
				return commitId;
			default:
				break;
			}

		}
		return null;

	}

	private boolean updateNoteMap(RevCommit cmit
			Map<String
		if (noteMap == null) {
			noteMap = NoteMap.newEmptyMap();
		}

		if (!noteMap.contains(cmit)) {
			StringBuilder footers = new StringBuilder();
			String footerKey = SubtreeSplitter.SUBTREE_FOOTER_KEY.getName();
			for (String subtreeId : subtreeParents.keySet()) {
				String sha1 = subtreeParents.get(subtreeId).name();
				footers.append(footerKey).append(": ").append(sha1).append(" ")
						.append(subtreeId).append('\n');
			}

			noteMap.set(cmit

			return true;
		} else {
			return false;
		}
	}

}
