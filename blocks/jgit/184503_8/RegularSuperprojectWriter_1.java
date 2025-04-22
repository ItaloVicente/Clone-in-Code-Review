package org.eclipse.jgit.gitrepo;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.R_TAGS;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.gitrepo.RepoCommand.ManifestErrorException;
import org.eclipse.jgit.gitrepo.RepoCommand.RemoteFile;
import org.eclipse.jgit.gitrepo.RepoCommand.RemoteReader;
import org.eclipse.jgit.gitrepo.RepoCommand.RemoteUnavailableException;
import org.eclipse.jgit.gitrepo.RepoProject.CopyFile;
import org.eclipse.jgit.gitrepo.RepoProject.LinkFile;
import org.eclipse.jgit.gitrepo.internal.RepoText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FileUtils;

class BareSuperprojectWriter {
	private static final int LOCK_FAILURE_MAX_RETRIES = 5;

	private static final int LOCK_FAILURE_MIN_RETRY_DELAY_MILLIS = 50;

	private static final int LOCK_FAILURE_MAX_RETRY_DELAY_MILLIS = 5000;

	private final Repository repo;

	private final URI targetUri;

	private final String targetBranch;

	private final RemoteReader callback;

	private final BareWriterConfig config;

	private final PersonIdent author;

	static class BareWriterConfig {
		boolean ignoreRemoteFailures = false;

		boolean recordRemoteBranch = true;

		boolean recordSubmoduleLabels = true;

		boolean recordShallowSubmodules = true;

		static BareWriterConfig getDefault() {
			return new BareWriterConfig();
		}

		private BareWriterConfig() {
		}
	}

	BareSuperprojectWriter(Repository repo
			String targetBranch
			PersonIdent author
			BareWriterConfig config) {
		assert (repo.isBare());
		this.repo = repo;
		this.targetUri = targetUri;
		this.targetBranch = targetBranch;
		this.author = author;
		this.callback = callback;
		this.config = config;
	}

	RevCommit write(List<RepoProject> repoProjects)
			throws GitAPIException {
		DirCache index = DirCache.newInCore();
		ObjectInserter inserter = repo.newObjectInserter();

		try (RevWalk rw = new RevWalk(repo)) {
			prepareIndex(repoProjects
			ObjectId treeId = index.writeTree(inserter);
			long prevDelay = 0;
			for (int i = 0; i < LOCK_FAILURE_MAX_RETRIES - 1; i++) {
				try {
					return commitTreeOnCurrentTip(inserter
				} catch (ConcurrentRefUpdateException e) {
					prevDelay = FileUtils.delay(prevDelay
							LOCK_FAILURE_MIN_RETRY_DELAY_MILLIS
							LOCK_FAILURE_MAX_RETRY_DELAY_MILLIS);
					Thread.sleep(prevDelay);
					repo.getRefDatabase().refresh();
				}
			}
			return commitTreeOnCurrentTip(inserter
		} catch (IOException | InterruptedException e) {
			throw new ManifestErrorException(e);
		}
	}

	private void prepareIndex(List<RepoProject> projects
			ObjectInserter inserter) throws IOException
		Config cfg = new Config();
		StringBuilder attributes = new StringBuilder();
		DirCacheBuilder builder = index.builder();
		for (RepoProject proj : projects) {
			String name = proj.getName();
			String path = proj.getPath();
			String url = proj.getUrl();
			ObjectId objectId;
			if (ObjectId.isId(proj.getRevision())) {
				objectId = ObjectId.fromString(proj.getRevision());
			} else {
				objectId = callback.sha1(url
				if (objectId == null && !config.ignoreRemoteFailures) {
					throw new RemoteUnavailableException(url);
				}
				if (config.recordRemoteBranch) {
					cfg.setString("submodule"
							proj.getRevision());
				}

				if (config.recordShallowSubmodules
						&& proj.getRecommendShallow() != null) {
					cfg.setBoolean("submodule"
							true);
				}
			}
			if (config.recordSubmoduleLabels) {
				StringBuilder rec = new StringBuilder();
				rec.append(path);
				for (String group : proj.getGroups()) {
					rec.append(group);
				}
				attributes.append(rec.toString());
			}

			URI submodUrl = URI.create(url);
			if (targetUri != null) {
				submodUrl = RepoCommand.relativize(targetUri
			}
			cfg.setString("submodule"
			cfg.setString("submodule"
					submodUrl.toString());

			if (objectId != null) {
				DirCacheEntry dcEntry = new DirCacheEntry(path);
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.GITLINK);
				builder.add(dcEntry);

				for (CopyFile copyfile : proj.getCopyFiles()) {
					RemoteFile rf = callback.readFileWithMode(url
							proj.getRevision()
					objectId = inserter.insert(Constants.OBJ_BLOB
							rf.getContents());
					dcEntry = new DirCacheEntry(copyfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(rf.getFileMode());
					builder.add(dcEntry);
				}
				for (LinkFile linkfile : proj.getLinkFiles()) {
					String link;
						link = FileUtils.relativizeGitPath(
								linkfile.dest.substring(0
										linkfile.dest.lastIndexOf('/'))
					} else {
					}

					objectId = inserter.insert(Constants.OBJ_BLOB
							link.getBytes(UTF_8));
					dcEntry = new DirCacheEntry(linkfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(FileMode.SYMLINK);
					builder.add(dcEntry);
				}
			}
		}
		String content = cfg.toText();

		DirCacheEntry dcEntry = new DirCacheEntry(
				Constants.DOT_GIT_MODULES);
		ObjectId objectId = inserter.insert(Constants.OBJ_BLOB
				content.getBytes(UTF_8));
		dcEntry.setObjectId(objectId);
		dcEntry.setFileMode(FileMode.REGULAR_FILE);
		builder.add(dcEntry);

		if (config.recordSubmoduleLabels) {
			DirCacheEntry dcEntryAttr = new DirCacheEntry(
					Constants.DOT_GIT_ATTRIBUTES);
			ObjectId attrId = inserter.insert(Constants.OBJ_BLOB
					attributes.toString().getBytes(UTF_8));
			dcEntryAttr.setObjectId(attrId);
			dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
			builder.add(dcEntryAttr);
		}

		builder.finish();
	}

	private RevCommit commitTreeOnCurrentTip(ObjectInserter inserter
			RevWalk rw
			throws IOException
		if (headId != null
				&& rw.parseCommit(headId).getTree().getId().equals(treeId)) {
			return rw.parseCommit(headId);
		}

		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(treeId);
		if (headId != null) {
			commit.setParentIds(headId);
		}
		commit.setAuthor(author);
		commit.setCommitter(author);
		commit.setMessage(RepoText.get().repoCommitMessage);

		ObjectId commitId = inserter.insert(commit);
		inserter.flush();

		RefUpdate ru = repo.updateRef(targetBranch);
		ru.setNewObjectId(commitId);
		ru.setExpectedOldObjectId(headId != null ? headId : ObjectId.zeroId());
		Result rc = ru.update(rw);
		switch (rc) {
		case NEW:
		case FORCED:
		case FAST_FORWARD:
			break;
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(MessageFormat.format(
					JGitText.get().cannotLock
		default:
			throw new JGitInternalException(
					MessageFormat.format(JGitText.get().updatingRefFailed
							targetBranch
		}

		return rw.parseCommit(commitId);
	}
}
