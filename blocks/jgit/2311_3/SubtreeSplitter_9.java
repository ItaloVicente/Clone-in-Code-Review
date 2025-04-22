
package org.eclipse.jgit.subtree;

import static org.eclipse.jgit.subtree.SubtreeContext.NO_SUBTREE;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.BlobBasedConfig;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FooterKey;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;

public class SubtreeSplitter {

	static final FooterKey SUBTREE_FOOTER_KEY = new FooterKey("Sub-Tree");

	private static final String SUBTREE_CONFIG = ".gitsubtree";

	public final static String SUBTREE_SECTION = "subtree";

	public static String SUBTREE_PATH_PROP = "path";

	public static final String SUBTREE_URL_PROP = "url";

	public static final Set<ObjectId> REWRITE_ALL = Collections
			.unmodifiableSet(new HashSet<ObjectId>());

	private Repository db;

	private RevWalk revWalk;

	private Map<String

	private Map<ObjectId

	public SubtreeSplitter(Repository db
		this.db = db;
		if (revWalk == null) {
			revWalk = new RevWalk(db);
		}
		this.revWalk = revWalk;
	}

	public void splitSubtrees(ObjectId start
			List<PathBasedContext> pathContexts
			Set<? extends ObjectId> toRewrite) throws IOException {

		subtreeContexts = new HashMap<String
		if (pathContexts != null) {
			for (PathBasedContext pbc : pathContexts) {
				subtreeContexts.put(pbc.getId()
			}
		}

		RevFilter oldFilter = revWalk.getRevFilter();
		ObjectInserter inserter = null;
		SubtreeRevFilter filter = null;
		try {
			inserter = db.newObjectInserter();
			filter = new SubtreeRevFilter(db);
			ArrayList<RevCommit> superList = splitSubtrees(inserter
					filter);
			if (toRewrite != null) {
				rewrittenCommits = rewriteSuperCommits(inserter
						toRewrite
			} else {
				rewrittenCommits = null;
			}
		} finally {
			if (filter != null) {
				try {
					filter.writeCache(revWalk);
				} catch (Exception e) {
				}
				filter.release();
			}
			revWalk.reset();
			revWalk.setRevFilter(oldFilter);
			if (inserter != null) {
				inserter.release();
			}
		}
	}

	ArrayList<RevCommit> splitSubtrees(ObjectInserter inserter
			ObjectId startCommit
			throws MissingObjectException
			IOException {

		revWalk.reset();
		revWalk.markStart(revWalk.parseCommit(startCommit));
		filter.setSplitters(subtreeContexts);
		revWalk.setRevFilter(filter);
		revWalk.sort(RevSort.TOPO);
		revWalk.sort(RevSort.REVERSE

		ArrayList<RevCommit> superList = new ArrayList<RevCommit>();
		ObjectReader or = revWalk.getObjectReader();
		for (RevCommit superCommit : revWalk) {

			superList.add(superCommit);
			RevCommit[] superParents = superCommit.getParents();
			RevTree superTree = superCommit.getTree();
			Config conf = loadSubtreeConfig(db
			Map<String
					.getSubtreeAnalyzer().getSubtreeParents(superCommit
							revWalk);

			for (SubtreeContext context : subtreeContexts.values()) {

				if (context.getSplitCommit(superCommit) != null) {
					throw new IOException("Tree walked out of order");
				}

				String subtreePath = context.getPath(conf);
				if (subtreePath == null) {
					context.setSplitCommit(superCommit
					continue;
				}

				TreeWalk treeWalk = TreeWalk
						.forPath(or
				if (treeWalk == null) {
					context.setSplitCommit(superCommit
					continue;
				}
				ObjectId subtreeTree = treeWalk.getObjectId(0);

				RevCommit contextParent = allSubtreeParents
						.get(context.getId());
				if (contextParent != null
						&& subtreeTree.equals(contextParent.getTree())) {
					context.setSplitCommit(superCommit
					continue;
				}

				ArrayList<RevCommit> subParents = new ArrayList<RevCommit>();
				for (ObjectId parent : superParents) {
					RevCommit subParent = context.getSplitCommit(parent);
					if (subParent != NO_SUBTREE && subParent != null
							&& !subParents.contains(subParent)) {
						subParents.add(subParent);
					}
				}

				if (subParents.size() > 1) {
					RevWalk rw2 = new RevWalk(revWalk.getObjectReader());
					rw2.sort(RevSort.TOPO);
					RevFlag REACHABLE = rw2.newFlag("REACHABLE");
					for (RevCommit parent : subParents) {
						for (RevCommit parentParent : rw2.parseCommit(parent)
								.getParents()) {
							rw2.markStart(parentParent);
							parentParent.add(REACHABLE);
						}
					}
					rw2.carry(REACHABLE);
					while (rw2.next() != null) {
					}
					ListIterator<RevCommit> i = subParents.listIterator();
					while (i.hasNext()) {
						if (rw2.parseCommit(i.next()).has(REACHABLE)) {
							i.remove();
						}
					}
				}

				if (subParents.size() == 1) {
					RevCommit singleParent = subParents.iterator().next();
					if (singleParent.getTree().equals(subtreeTree)) {
						context.setSplitCommit(superCommit
								revWalk.parseCommit(singleParent));
						continue;
					}
				}

				CommitBuilder cb = new CommitBuilder();
				cb.setParentIds(subParents);
				cb.setAuthor(superCommit.getAuthorIdent());
				cb.setCommitter(superCommit.getCommitterIdent());
				cb.setEncoding(superCommit.getEncoding());
				cb.setTreeId(subtreeTree);
				cb.setMessage(superCommit.getFullMessage());
				context.setSplitCommit(superCommit
						revWalk.parseCommit(inserter.insert(cb)));
			}
		}
		return superList;
	}

	Map<ObjectId
			List<RevCommit> superList
			SubtreeRevFilter filter) throws MissingObjectException
			IncorrectObjectTypeException

		Map<ObjectId
		SubtreeAnalyzer sa = filter.getSubtreeAnalyzer();

		for (RevCommit curCommit : superList) {
			boolean rewriteCommit = false;
			if (toRewrite == REWRITE_ALL || toRewrite.contains(curCommit)) {
				rewriteCommit = true;
			} else {
				for (RevCommit parent : curCommit.getParents()) {
					if (toRewrite.contains(parent)) {
						rewriteCommit = true;
					}
				}
			}
			if (rewriteCommit) {
				ObjectId newCommitId = rewriteSuperCommit(inserter
						curCommit
				rewriteMap.put(curCommit
			}
		}
		return rewriteMap;
	}

	ObjectId rewriteSuperCommit(ObjectInserter inserter
			Map<ObjectId
			SubtreeAnalyzer sa) throws MissingObjectException
			IncorrectObjectTypeException

		RevCommit commit = revWalk.parseCommit(commitId);
		CommitBuilder cb = new CommitBuilder();

		Map<String
				revWalk);
		for (RevCommit parent : commit.getParents()) {
			if (!subtreeParents.containsValue(parent)) {
				cb.addParentId(rewriteMap.containsKey(parent) ? rewriteMap
						.get(parent) : parent);
			}
		}

		for (SubtreeContext subtreeContext : subtreeContexts.values()) {

			RevCommit subtreeParentCandidate = subtreeContext
					.getSplitCommit(commit);
			if (subtreeParentCandidate == null
					|| subtreeParentCandidate == NO_SUBTREE) {
				continue;
			}

			boolean subtreeChanged = true;
			for (RevCommit superParent : commit.getParents()) {
				if (subtreeParents.containsValue(superParent)) {
					continue;
				}
				if (subtreeParentCandidate.equals(subtreeContext
						.getSplitCommit(superParent))) {
					subtreeChanged = false;
					break;
				}
			}
			if (subtreeChanged) {
				cb.addParentId(subtreeParentCandidate);
			}
		}

		cb.setTreeId(updateSubtreeConfig(db
				inserter

		if (cb.getTreeId().equals(commit.getTree())) {
			HashSet<ObjectId> parents1 = new HashSet<ObjectId>();
			for (ObjectId parent1 : commit.getParents())
				parents1.add(parent1);
			HashSet<ObjectId> parents2 = new HashSet<ObjectId>();
			for (ObjectId parent2 : cb.getParentIds())
				parents2.add(parent2);
			if (parents1.equals(parents2)) {
				return commit;
			}
		}

		cb.setAuthor(commit.getAuthorIdent());
		cb.setCommitter(commit.getCommitterIdent());
		cb.setEncoding(commit.getEncoding());
		cb.setMessage(commit.getFullMessage());
		return inserter.insert(cb);
	}

	static ObjectId updateSubtreeConfig(Repository db
			Collection<SubtreeContext> subtreeContexts
			ObjectInserter inserter

		RevObject obj = revWalk.parseAny(treeish);
		RevTree tree;
		if (obj instanceof RevTree) {
			tree = (RevTree) obj;
		} else if (obj instanceof RevCommit) {
			tree = ((RevCommit) obj).getTree();
		} else {
			throw new IOException("Can't handle type: " + obj);
		}

		Config config = loadSubtreeConfig(db

		boolean madeChange = false;
		for (SubtreeContext context : subtreeContexts) {
			String path = context.getPath(config);
			String existingPath = config.getString(SUBTREE_SECTION
					context.getId()

			if (path == null || path.trim().isEmpty()) {
				if (existingPath != null && !existingPath.trim().isEmpty()) {
					madeChange = true;
					config.unset(SUBTREE_SECTION
							SUBTREE_PATH_PROP);
				}
			} else {
				if (!path.equals(existingPath)) {
					madeChange = true;
					config.setString(SUBTREE_SECTION
							SUBTREE_PATH_PROP
				}
			}
		}

		if (!madeChange)
			return tree;

		final ObjectId configId = inserter.insert(Constants.OBJ_BLOB
				Constants.encode(config.toText()));

		DirCache dirCache = DirCache.newInCore();
		DirCacheBuilder builder = dirCache.builder();
		builder.addTree(new byte[0]
		builder.finish();

		DirCacheEditor editor = dirCache.editor();
		editor.add(new PathEdit(SUBTREE_CONFIG) {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setObjectId(configId);
				ent.setFileMode(FileMode.REGULAR_FILE);
			}
		});
		editor.finish();

		return editor.getDirCache().writeTree(inserter);
	}

	static Config loadSubtreeConfig(Repository db
			throws IOException {
		try {
			return new BlobBasedConfig(null
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			throw new IOException("Unable to load " + SUBTREE_CONFIG
					+ " config file for commit " + treeish.name());
		} catch (ConfigInvalidException e) {
			throw new IOException("Invalid " + SUBTREE_CONFIG
					+ " config file for commit " + treeish.name());
		}
		return new Config();
	}

	public Map<String
		return subtreeContexts;
	}

	public Map<ObjectId
		return rewrittenCommits;
	}

}
