package org.eclipse.jgit.niofs.internal.op;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.internal.ketch.KetchLeader;
import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
import org.eclipse.jgit.niofs.internal.JGitPathImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.Fork;
import org.eclipse.jgit.niofs.internal.op.commands.SubdirectoryClone;
import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
import org.eclipse.jgit.niofs.internal.op.model.TextualDiff;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;

public interface Git {

	static Git createRepository(final File repoDir) throws IOException {
		return createRepository(repoDir
	}

	static Git createRepository(final File repoDir
		return createRepository(repoDir
	}

	static Git createRepository(final File repoDir
		return createRepository(repoDir
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
	}

	static Git createRepository(final File repoDir
		return createRepository(repoDir
	}

	static Git createRepository(final File repoDir
			throws IOException {
		return new CreateRepository(repoDir
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
	}

	static Git createRepository(final File repoDir
			final boolean sslVerify) {
		try {
			return new CreateRepository(repoDir
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static Git fork(final File gitRepoContainerDir
			final CredentialsProvider credential
			throws IOException {
		return new Fork(gitRepoContainerDir
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
	}

	static Git fork(final File gitRepoContainerDir
			final CredentialsProvider credential
			final boolean sslVerify) throws IOException {
		return new Fork(gitRepoContainerDir
	}

	static Git clone(final File repoDest
			final CredentialsProvider credential
			throws IOException {
		return new Clone(repoDest
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
	}

	static Git clone(final File repoDest
			final CredentialsProvider credential
			final boolean sslVerify) throws IOException {
		return new Clone(repoDest
	}

	static Git cloneSubdirectory(final File repoDest
			final List<String> branches
			final File hookDir) throws IOException {
		return new SubdirectoryClone(repoDest
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
	}

	static Git cloneSubdirectory(final File repoDest
			final List<String> branches
			final File hookDir
		return new SubdirectoryClone(repoDest
				.execute();
	}

	void convertRefTree();

	void deleteRef(final Ref ref) throws IOException;

	Ref getRef(final String ref);

	void push(final CredentialsProvider credentialsProvider
			final boolean force

	void gc();

	RevCommit getCommit(final String commitId);

	RevCommit getLastCommit(final String refName);

	RevCommit getLastCommit(final Ref ref) throws IOException;

	RevCommit getCommonAncestorCommit(final String branchA

	CommitHistory listCommits(final Ref ref

	List<RevCommit> listCommits(final String startCommitId

	List<RevCommit> listCommits(final ObjectId startRange

	Repository getRepository();

	ObjectId getTreeFromRef(final String treeRef);

	void fetch(final CredentialsProvider credential
			final Collection<RefSpec> refSpecs) throws InvalidRemoteException;

	void syncRemote(final Map.Entry<String

	List<String> merge(final String source

	List<String> merge(final String source

	boolean revertMerge(final String source
			final String mergeCommitId);

	void cherryPick(final JGitPathImpl target

	void cherryPick(final String targetBranch

	void createRef(final String source

	List<FileDiff> diffRefs(final String branchA

	List<TextualDiff> textualDiffRefs(final String branchA

	List<TextualDiff> textualDiffRefs(final String branchA
			final String commitIdBranchB);

	List<String> conflictBranchesChecker(final String branchA

	void squash(final String branch

	boolean commit(final String branchName
			final CommitContent content);

	List<DiffEntry> listDiffs(final String startCommitId

	List<DiffEntry> listDiffs(final ObjectId refA

	Map<String

	InputStream blobAsInputStream(final String treeRef

	RevCommit getFirstCommit(final Ref ref) throws IOException;

	List<Ref> listRefs();

	List<ObjectId> resolveObjectIds(final String... commits);

	RevCommit resolveRevCommit(final ObjectId objectId) throws IOException;

	List<RefSpec> updateRemoteConfig(final Map.Entry<String
			throws IOException

	PathInfo getPathInfo(final String branchName

	List<PathInfo> listPathContent(final String branchName

	boolean isHEADInitialized();

	void setHeadAsInitialized();

	void refUpdate(final String branch

	KetchLeader getKetchLeader();

	boolean isKetchEnabled();

	void enableKetch();

	void updateRepo(Repository repo);

	void updateLeaders(final KetchLeaderCache leaders);
}
