package org.eclipse.jgit.gitrepo;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.TreeMap;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.gitrepo.ManifestParser.IncludedFileReader;
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
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;

public class RepoCommand extends GitCommand<RevCommit> {
	private String manifestPath;
	private String baseUri;
	private URI targetUri;
	private String groupsParam;
	private String branch;
	private String targetBranch = Constants.HEAD;
	private boolean recordRemoteBranch = true;
	private boolean recordSubmoduleLabels = true;
	private boolean recordShallowSubmodules = true;
	private PersonIdent author;
	private RemoteReader callback;
	private InputStream inputStream;
	private IncludedFileReader includedReader;
	private boolean ignoreRemoteFailures = false;

	private ProgressMonitor monitor;

	public interface RemoteReader {
		@Nullable
		public ObjectId sha1(String uri

		@Deprecated
		public default byte[] readFile(String uri
				throws GitAPIException
			return readFileWithMode(uri
		}

		@NonNull
		public RemoteFile readFileWithMode(String uri
				throws GitAPIException
	}

	public static final class RemoteFile {
		@NonNull
		private final byte[] contents;

		@NonNull
		private final FileMode fileMode;

		public RemoteFile(@NonNull byte[] contents
				@NonNull FileMode fileMode) {
			this.contents = Objects.requireNonNull(contents);
			this.fileMode = Objects.requireNonNull(fileMode);
		}

		@NonNull
		public byte[] getContents() {
			return contents;
		}

		@NonNull
		public FileMode getFileMode() {
			return fileMode;
		}

	}

	public static class DefaultRemoteReader implements RemoteReader {

		@Override
		public ObjectId sha1(String uri
			Map<String
					.lsRemoteRepository()
					.setRemote(uri)
					.callAsMap();
			Ref r = RefDatabase.findRef(map
			return r != null ? r.getObjectId() : null;
		}

		@Override
		public RemoteFile readFileWithMode(String uri
				throws GitAPIException
			File dir = FileUtils.createTempDir("jgit_"
			try (Git git = Git.cloneRepository().setBare(true).setDirectory(dir)
					.setURI(uri).call()) {
				Repository repo = git.getRepository();
				ObjectId refCommitId = sha1(uri
				if (refCommitId == null) {
					throw new InvalidRefNameException(MessageFormat
							.format(JGitText.get().refNotResolved
				}
				RevCommit commit = repo.parseCommit(refCommitId);
				TreeWalk tw = TreeWalk.forPath(repo

				return new RemoteFile(
						tw.getObjectReader().open(tw.getObjectId(0))
								.getCachedBytes(Integer.MAX_VALUE)
						tw.getFileMode(0));
			} finally {
				FileUtils.delete(dir
			}
		}
	}

	@SuppressWarnings("serial")
	private static class ManifestErrorException extends GitAPIException {
		ManifestErrorException(Throwable cause) {
			super(RepoText.get().invalidManifest
		}
	}

	@SuppressWarnings("serial")
	private static class RemoteUnavailableException extends GitAPIException {
		RemoteUnavailableException(String uri) {
			super(MessageFormat.format(RepoText.get().errorRemoteUnavailable
		}
	}

	public RepoCommand(Repository repo) {
		super(repo);
	}

	public RepoCommand setPath(String path) {
		this.manifestPath = path;
		return this;
	}

	public RepoCommand setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}

	public RepoCommand setURI(String uri) {
		this.baseUri = uri;
		return this;
	}

	public RepoCommand setTargetURI(String uri) {
		return this;
	}

	public RepoCommand setGroups(String groups) {
		this.groupsParam = groups;
		return this;
	}

	public RepoCommand setBranch(String branch) {
		this.branch = branch;
		return this;
	}

	public RepoCommand setTargetBranch(String branch) {
		this.targetBranch = Constants.R_HEADS + branch;
		return this;
	}

	public RepoCommand setRecordRemoteBranch(boolean enable) {
		this.recordRemoteBranch = enable;
		return this;
	}

	public RepoCommand setRecordSubmoduleLabels(boolean enable) {
		this.recordSubmoduleLabels = enable;
		return this;
	}

	public RepoCommand setRecommendShallow(boolean enable) {
		this.recordShallowSubmodules = enable;
		return this;
	}

	public RepoCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public RepoCommand setIgnoreRemoteFailures(boolean ignore) {
		this.ignoreRemoteFailures = ignore;
		return this;
	}

	public RepoCommand setAuthor(PersonIdent author) {
		this.author = author;
		return this;
	}

	public RepoCommand setRemoteReader(RemoteReader callback) {
		this.callback = callback;
		return this;
	}

	public RepoCommand setIncludedFileReader(IncludedFileReader reader) {
		this.includedReader = reader;
		return this;
	}

	@Override
	public RevCommit call() throws GitAPIException {
		checkCallable();
		if (baseUri == null) {
		}
		if (inputStream == null) {
			if (manifestPath == null || manifestPath.length() == 0)
				throw new IllegalArgumentException(
						JGitText.get().pathNotConfigured);
			try {
				inputStream = new FileInputStream(manifestPath);
			} catch (IOException e) {
				throw new IllegalArgumentException(
						JGitText.get().pathNotConfigured);
			}
		}

		List<RepoProject> filteredProjects;
		try {
			ManifestParser parser = new ManifestParser(includedReader
					manifestPath
			parser.read(inputStream);
			filteredProjects = parser.getFilteredProjects();
		} catch (IOException e) {
			throw new ManifestErrorException(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}

		if (repo.isBare()) {
			if (author == null)
				author = new PersonIdent(repo);
			if (callback == null)
				callback = new DefaultRemoteReader();
			List<RepoProject> renamedProjects = renameProjects(filteredProjects);

			DirCache index = DirCache.newInCore();
			DirCacheBuilder builder = index.builder();
			ObjectInserter inserter = repo.newObjectInserter();
			try (RevWalk rw = new RevWalk(repo)) {
				Config cfg = new Config();
				StringBuilder attributes = new StringBuilder();
				for (RepoProject proj : renamedProjects) {
					String name = proj.getName();
					String path = proj.getPath();
					String url = proj.getUrl();
					ObjectId objectId;
					if (ObjectId.isId(proj.getRevision())) {
						objectId = ObjectId.fromString(proj.getRevision());
					} else {
						objectId = callback.sha1(url
						if (objectId == null && !ignoreRemoteFailures) {
							throw new RemoteUnavailableException(url);
						}
						if (recordRemoteBranch) {
							cfg.setString("submodule"
									proj.getRevision());
						}

						if (recordShallowSubmodules && proj.getRecommendShallow() != null) {
							cfg.setBoolean("submodule"
									true);
						}
					}
					if (recordSubmoduleLabels) {
						StringBuilder rec = new StringBuilder();
						rec.append(path);
						for (String group : proj.getGroups()) {
							rec.append(group);
						}
						attributes.append(rec.toString());
					}

					URI submodUrl = URI.create(url);
					if (targetUri != null) {
						submodUrl = relativize(targetUri
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
							RemoteFile rf = callback.readFileWithMode(
								url
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

				final DirCacheEntry dcEntry = new DirCacheEntry(Constants.DOT_GIT_MODULES);
				ObjectId objectId = inserter.insert(Constants.OBJ_BLOB
						content.getBytes(UTF_8));
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.REGULAR_FILE);
				builder.add(dcEntry);

				if (recordSubmoduleLabels) {
					final DirCacheEntry dcEntryAttr = new DirCacheEntry(Constants.DOT_GIT_ATTRIBUTES);
					ObjectId attrId = inserter.insert(Constants.OBJ_BLOB
							attributes.toString().getBytes(UTF_8));
					dcEntryAttr.setObjectId(attrId);
					dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
					builder.add(dcEntryAttr);
				}

				builder.finish();
				ObjectId treeId = index.writeTree(inserter);

				if (headId != null && rw.parseCommit(headId).getTree().getId().equals(treeId)) {
					return rw.parseCommit(headId);
				}

				CommitBuilder commit = new CommitBuilder();
				commit.setTreeId(treeId);
				if (headId != null)
					commit.setParentIds(headId);
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
						throw new ConcurrentRefUpdateException(
								MessageFormat.format(
										JGitText.get().cannotLock
								ru.getRef()
								rc);
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().updatingRefFailed
								targetBranch
				}

				return rw.parseCommit(commitId);
			} catch (GitAPIException | IOException e) {
				throw new ManifestErrorException(e);
			}
		} else {
			try (Git git = new Git(repo)) {
				for (RepoProject proj : filteredProjects) {
					addSubmodule(proj.getName()
							proj.getRevision()
							proj.getLinkFiles()
				}
				return git.commit().setMessage(RepoText.get().repoCommitMessage)
						.call();
			} catch (GitAPIException | IOException e) {
				throw new ManifestErrorException(e);
			}
		}
	}

	private void addSubmodule(String name
			String revision
			Git git) throws GitAPIException
		assert (!repo.isBare());
		assert (git != null);
		if (!linkfiles.isEmpty()) {
			throw new UnsupportedOperationException(
					JGitText.get().nonBareLinkFilesNotSupported);
		}

		SubmoduleAddCommand add = git.submoduleAdd().setName(name).setPath(path)
				.setURI(url);
		if (monitor != null)
			add.setProgressMonitor(monitor);

		Repository subRepo = add.call();
		if (revision != null) {
			try (Git sub = new Git(subRepo)) {
				sub.checkout().setName(findRef(revision
			}
			subRepo.close();
			git.add().addFilepattern(path).call();
		}
		for (CopyFile copyfile : copyfiles) {
			copyfile.copy();
			git.add().addFilepattern(copyfile.dest).call();
		}
	}

	private List<RepoProject> renameProjects(List<RepoProject> projects) {
		Map<String
		for (RepoProject proj : projects) {
			List<RepoProject> l = m.get(proj.getName());
			if (l == null) {
				l = new ArrayList<>();
				m.put(proj.getName()
			}
			l.add(proj);
		}

		List<RepoProject> ret = new ArrayList<>();
		for (List<RepoProject> ps : m.values()) {
			boolean nameConflict = ps.size() != 1;
			for (RepoProject proj : ps) {
				String name = proj.getName();
				if (nameConflict) {
					name += SLASH + proj.getPath();
				}
				RepoProject p = new RepoProject(name
						proj.getPath()
						proj.getGroups()
				p.setUrl(proj.getUrl());
				p.addCopyFiles(proj.getCopyFiles());
				p.addLinkFiles(proj.getLinkFiles());
				ret.add(p);
			}
		}
		return ret;
	}

	static URI relativize(URI current
		if (!Objects.equals(current.getHost()
			return target;
		}

		String cur = current.normalize().getPath();
		String dest = target.normalize().getPath();

		if (cur.startsWith(SLASH) != dest.startsWith(SLASH)) {
			return target;
		}

		while (cur.startsWith(SLASH)) {
			cur = cur.substring(1);
		}
		while (dest.startsWith(SLASH)) {
			dest = dest.substring(1);
		}

		if (cur.indexOf('/') == -1 || dest.indexOf('/') == -1) {
			cur = prefix + cur;
			dest = prefix + dest;
		}

		if (!cur.endsWith(SLASH)) {
			int lastSlash = cur.lastIndexOf('/');
			cur = cur.substring(0
		}
		if (!dest.endsWith(SLASH)) {
			int lastSlash = dest.lastIndexOf('/');
			destFile = dest.substring(lastSlash + 1
			dest = dest.substring(0
		}

		String[] cs = cur.split(SLASH);
		String[] ds = dest.split(SLASH);

		int common = 0;
		while (common < cs.length && common < ds.length && cs[common].equals(ds[common])) {
			common++;
		}

		StringJoiner j = new StringJoiner(SLASH);
		for (int i = common; i < cs.length; i++) {
		}
		for (int i = common; i < ds.length; i++) {
			j.add(ds[i]);
		}

		j.add(destFile);
		return URI.create(j.toString());
	}

	private static String findRef(String ref
			throws IOException {
		if (!ObjectId.isId(ref)) {
			if (r != null)
				return r.getName();
		}
		return ref;
	}
}
