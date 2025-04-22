package org.eclipse.jgit.api;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.DeletePath;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.IndexDiffFilter;
import org.eclipse.jgit.treewalk.filter.SkipWorkTreeFilter;

public class StashCreateCommand extends GitCommand<RevCommit> {

	private static final String MSG_INDEX = "index on {0}: {1} {2}";

	private static final String MSG_WORKING_DIR = "WIP on {0}: {1} {2}";

	private String indexMessage = MSG_INDEX;

	private String workingDirectoryMessage = MSG_WORKING_DIR;

	private String ref = Constants.R_STASH;

	private PersonIdent person;

	public StashCreateCommand(Repository repo) {
		super(repo);
		person = new PersonIdent(repo);
	}

	public StashCreateCommand setIndexMessage(String message) {
		indexMessage = message;
		return this;
	}

	public StashCreateCommand setWorkingDirectoryMessage(String message) {
		workingDirectoryMessage = message;
		return this;
	}

	public StashCreateCommand setPerson(PersonIdent person) {
		this.person = person;
		return this;
	}

	public StashCreateCommand setRef(String ref) {
		this.ref = ref;
		return this;
	}

	private RevCommit parseCommit(final ObjectReader reader
			final ObjectId headId) throws IOException {
		final RevWalk walk = new RevWalk(reader);
		walk.setRetainBody(true);
		return walk.parseCommit(headId);
	}

	private CommitBuilder createBuilder(ObjectId headId) {
		CommitBuilder builder = new CommitBuilder();
		PersonIdent author = person;
		if (author == null)
			author = new PersonIdent(repo);
		builder.setAuthor(author);
		builder.setCommitter(author);
		builder.setParentId(headId);
		return builder;
	}

	private void updateStashRef(ObjectId commitId
			String refLogMessage) throws IOException {
		Ref currentRef = repo.getRef(ref);
		RefUpdate refUpdate = repo.updateRef(ref);
		refUpdate.setNewObjectId(commitId);
		refUpdate.setRefLogIdent(refLogIdent);
		refUpdate.setRefLogMessage(refLogMessage
		if (currentRef != null)
			refUpdate.setExpectedOldObjectId(currentRef.getObjectId());
		else
			refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
		refUpdate.forceUpdate();
	}

	private Ref getHead() throws GitAPIException {
		try {
			Ref head = repo.getRef(Constants.HEAD);
			if (head == null || head.getObjectId() == null)
				throw new NoHeadException(JGitText.get().headRequiredToStash);
			return head;
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashFailed
		}
	}

	public RevCommit call() throws GitAPIException {
		checkCallable();

		Ref head = getHead();
		ObjectReader reader = repo.newObjectReader();
		try {
			RevCommit headCommit = parseCommit(reader
			DirCache cache = repo.lockDirCache();
			ObjectInserter inserter = repo.newObjectInserter();
			ObjectId commitId;
			try {
				TreeWalk treeWalk = new TreeWalk(reader);
				treeWalk.setRecursive(true);
				treeWalk.addTree(headCommit.getTree());
				treeWalk.addTree(new DirCacheIterator(cache));
				treeWalk.addTree(new FileTreeIterator(repo));
				treeWalk.setFilter(AndTreeFilter.create(new SkipWorkTreeFilter(
						1)

				if (!treeWalk.next())
					return null;

				MutableObjectId id = new MutableObjectId();
				List<PathEdit> wtEdits = new ArrayList<PathEdit>();
				List<String> wtDeletes = new ArrayList<String>();
				do {
					AbstractTreeIterator headIter = treeWalk.getTree(0
							AbstractTreeIterator.class);
					DirCacheIterator indexIter = treeWalk.getTree(1
							DirCacheIterator.class);
					WorkingTreeIterator wtIter = treeWalk.getTree(2
							WorkingTreeIterator.class);
					if (headIter != null && indexIter != null && wtIter != null) {
						if (wtIter.idEqual(indexIter)
								|| wtIter.idEqual(headIter))
							continue;
						treeWalk.getObjectId(id
						final DirCacheEntry entry = new DirCacheEntry(
								treeWalk.getRawPath());
						entry.setLength(wtIter.getEntryLength());
						entry.setLastModified(wtIter.getEntryLastModified());
						entry.setFileMode(wtIter.getEntryFileMode());
						long contentLength = wtIter.getEntryContentLength();
						InputStream in = wtIter.openEntryStream();
						try {
							entry.setObjectId(inserter.insert(
									Constants.OBJ_BLOB
						} finally {
							in.close();
						}
						wtEdits.add(new PathEdit(entry) {

							public void apply(DirCacheEntry ent) {
								ent.copyMetaData(entry);
							}
						});
					} else if (indexIter == null)
						wtDeletes.add(treeWalk.getPathString());
					else if (wtIter == null && headIter != null)
						wtDeletes.add(treeWalk.getPathString());
				} while (treeWalk.next());

				String branch = Repository.shortenRefName(head.getTarget()
						.getName());

				CommitBuilder builder = createBuilder(headCommit);
				builder.setTreeId(cache.writeTree(inserter));
				builder.setMessage(MessageFormat.format(indexMessage
						headCommit.abbreviate(7).name()
						headCommit.getShortMessage()));
				ObjectId indexCommit = inserter.insert(builder);

				if (!wtEdits.isEmpty() || !wtDeletes.isEmpty()) {
					DirCacheEditor editor = cache.editor();
					for (PathEdit edit : wtEdits)
						editor.add(edit);
					for (String path : wtDeletes)
						editor.add(new DeletePath(path));
					editor.finish();
				}
				builder.addParentId(indexCommit);
				builder.setMessage(MessageFormat.format(
						workingDirectoryMessage
						headCommit.abbreviate(7).name()
						headCommit.getShortMessage()));
				builder.setTreeId(cache.writeTree(inserter));
				commitId = inserter.insert(builder);
				inserter.flush();

				updateStashRef(commitId
						builder.getMessage());
			} finally {
				inserter.release();
				cache.unlock();
			}

			new ResetCommand(repo).setMode(ResetType.HARD).call();

			return parseCommit(reader
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashFailed
		} finally {
			reader.release();
		}
	}
}
