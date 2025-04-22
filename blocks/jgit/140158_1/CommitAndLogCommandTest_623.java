package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.submodule.SubmoduleStatusType;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Test;

public class CloneCommandTest extends RepositoryTestCase {

	private Git git;

	private TestRepository<Repository> tr;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<>(db);

		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();
		git.tag().setName("tag-initial").setMessage("Tag initial").call();

		git.checkout().setCreateBranch(true).setName("test").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Second commit").call();
		RevBlob blob = tr.blob("blob-not-in-master-branch");
		git.tag().setName("tag-for-blob").setObjectId(blob).call();
	}

	@Test
	public void testCloneRepository() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		ObjectId id = git2.getRepository().resolve("tag-for-blob");
		assertNotNull(id);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals(
				"origin"
				git2.getRepository()
						.getConfig()
						.getString(ConfigConstants.CONFIG_BRANCH_SECTION
								"test"
		assertEquals(
				"refs/heads/test"
				git2.getRepository()
						.getConfig()
						.getString(ConfigConstants.CONFIG_BRANCH_SECTION
								"test"
		assertEquals(2
				.size());
				fetchRefSpec(git2.getRepository()));
	}

	@Test
	public void testCloneRepositoryExplicitGitDir() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setGitDir(new File(directory
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(directory
		assertEquals(new File(directory
				.getDirectory());
	}

	@Test
	public void testCloneRepositoryDefaultDirectory()
			throws URISyntaxException
		CloneCommand command = Git.cloneRepository().setURI(fileUri());

		command.verifyDirectories(new URIish(fileUri()));
		File directory = command.getDirectory();
		assertEquals(git.getRepository().getWorkTree().getName()
	}

	@Test
	public void testCloneBareRepositoryDefaultDirectory()
			throws URISyntaxException
		CloneCommand command = Git.cloneRepository().setURI(fileUri()).setBare(true);

		command.verifyDirectories(new URIish(fileUri()));
		File directory = command.getDirectory();
		assertEquals(git.getRepository().getWorkTree().getName() + Constants.DOT_GIT_EXT
	}

	@Test
	public void testCloneRepositoryExplicitGitDirNonStd() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository");
		File gDir = createTempDirectory("testCloneRepository.git");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setGitDir(gDir);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(directory
		assertEquals(gDir
				.getDirectory());
		assertTrue(new File(directory
		assertFalse(new File(gDir
	}

	@Test
	public void testCloneRepositoryExplicitGitDirBare() throws IOException
			JGitInternalException
		File gDir = createTempDirectory("testCloneRepository.git");
		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setGitDir(gDir);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		try {
			assertNull(null
			fail("Expected NoWorkTreeException");
		} catch (NoWorkTreeException e) {
			assertEquals(gDir
		}
	}

	@Test
	public void testBareCloneRepository() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository_bare");
		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
				fetchRefSpec(git2.getRepository()));
	}

	@Test
	public void testCloneRepositoryCustomRemote() throws Exception {
		File directory = createTempDirectory("testCloneRemoteUpstream");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setRemote("upstream");
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
				git2.getRepository()
					.getConfig()
					.getStringList("remote"
							"fetch")[0]);
		assertEquals("upstream"
				git2.getRepository()
					.getConfig()
					.getString("branch"
		assertEquals(db.resolve("test")
				git2.getRepository().resolve("upstream/test"));
	}

	@Test
	public void testBareCloneRepositoryCustomRemote() throws Exception {
		File directory = createTempDirectory("testCloneRemoteUpstream_bare");
		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		command.setRemote("upstream");
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
				git2.getRepository()
					.getConfig()
					.getStringList("remote"
							"fetch")[0]);
		assertEquals("upstream"
				git2.getRepository()
					.getConfig()
					.getString("branch"
		assertNull(git2.getRepository().resolve("upstream/test"));
	}

	@Test
	public void testBareCloneRepositoryNullRemote() throws Exception {
		File directory = createTempDirectory("testCloneRemoteNull_bare");
		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		command.setRemote(null);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
				.getConfig().getStringList("remote"
		assertEquals("origin"
				.getString("branch"
	}

	public static RefSpec fetchRefSpec(Repository r) throws URISyntaxException {
		RemoteConfig remoteConfig =
				new RemoteConfig(r.getConfig()
		return remoteConfig.getFetchRefSpecs().get(0);
	}

	@Test
	public void testCloneRepositoryWithBranch() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals(
				"refs/heads/master
				allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));

		directory = createTempDirectory("testCloneRepositoryWithBranch_bare");
		command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setNoCheckout(true);
		git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master
				allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));

		directory = createTempDirectory("testCloneRepositoryWithBranch_bare");
		command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setBare(true);
		git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/heads/master
				.branchList().setListMode(ListMode.ALL).call()));
	}

	@Test
	public void testCloneRepositoryWithBranchShortName() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("test");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals("refs/heads/test"
	}

	@Test
	public void testCloneRepositoryWithTagName() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("tag-initial");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		ObjectId taggedCommit = db.resolve("tag-initial^{commit}");
		assertEquals(taggedCommit.name()
				.getRepository().getFullBranch());
	}

	@Test
	public void testCloneRepositoryOnlyOneBranch() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(Collections
				.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNull(git2.getRepository().resolve("tag-for-blob"));
		assertNotNull(git2.getRepository().resolve("tag-initial"));
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master"
				.branchList().setListMode(ListMode.REMOTE).call()));
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
		assertEquals(
				new RefSpec("+refs/heads/master:refs/remotes/origin/master")
				specs.get(0));
	}

	@Test
	public void testBareCloneRepositoryOnlyOneBranch() throws Exception {
		File directory = createTempDirectory(
				"testCloneRepositoryWithBranch_bare");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(Collections
				.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setBare(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNull(git2.getRepository().resolve("tag-for-blob"));
		assertNotNull(git2.getRepository().resolve("tag-initial"));
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/heads/master"
				.setListMode(ListMode.ALL).call()));
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
		assertEquals(
				new RefSpec("+refs/heads/master:refs/heads/master")
				specs.get(0));
	}

	@Test
	public void testCloneRepositoryOnlyOneTag() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("tag-initial");
		command.setBranchesToClone(
				Collections.singletonList("refs/tags/tag-initial"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNull(git2.getRepository().resolve("tag-for-blob"));
		assertNull(git2.getRepository().resolve("refs/heads/master"));
		assertNotNull(git2.getRepository().resolve("tag-initial"));
		ObjectId taggedCommit = db.resolve("tag-initial^{commit}");
		assertEquals(taggedCommit.name()
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
		assertEquals(
				new RefSpec("+refs/tags/tag-initial:refs/tags/tag-initial")
				specs.get(0));
	}

	public static String allRefNames(List<Ref> refs) {
		StringBuilder sb = new StringBuilder();
		for (Ref f : refs) {
			if (sb.length() > 0)
				sb.append("
			sb.append(f.getName());
		}
		return sb.toString();
	}

	@Test
	public void testCloneRepositoryWhenDestinationDirectoryExistsAndIsNotEmpty()
			throws IOException
		String dirName = "testCloneTargetDirectoryNotEmpty";
		File directory = createTempDirectory(dirName);
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		try {
			git2 = command.call();
			fail("destination directory already exists and is not an empty folder
		} catch (JGitInternalException e) {
			assertTrue(e.getMessage().contains("not an empty directory"));
			assertTrue(e.getMessage().contains(dirName));
		}
	}

	@Test
	public void testCloneRepositoryWithMultipleHeadBranches() throws Exception {
		git.checkout().setName(Constants.MASTER).call();
		git.branchCreate().setName("a").call();

		File directory = createTempDirectory("testCloneRepositoryWithMultipleHeadBranches");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setURI(fileUri());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER
	}

	@Test
	public void testCloneRepositoryWithSubmodules() throws Exception {
		git.checkout().setName(Constants.MASTER).call();

		String file = "file.txt";
		writeTrashFile(file
		git.add().addFilepattern(file).call();
		RevCommit commit = git.commit().setMessage("create file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		addRepoToClose(repo);
		git.add().addFilepattern(path)
				.addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("adding submodule").call();
		try (SubmoduleWalk walk = SubmoduleWalk.forIndex(git.getRepository())) {
			assertTrue(walk.next());
			Repository subRepo = walk.getRepository();
			addRepoToClose(subRepo);
			assertNotNull(subRepo);
			assertEquals(
					new File(git.getRepository().getWorkTree()
					subRepo.getWorkTree());
			assertEquals(new File(new File(git.getRepository().getDirectory()
					"modules")
		}

		File directory = createTempDirectory("testCloneRepositoryWithSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(true);
		clone.setURI(fileUri());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER
		assertTrue(new File(git2.getRepository().getWorkTree()
				+ File.separatorChar + file).exists());

		SubmoduleStatusCommand status = new SubmoduleStatusCommand(
				git2.getRepository());
		Map<String
		SubmoduleStatus pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED
		assertEquals(commit
		assertEquals(commit

		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			Repository clonedSub1 = walk.getRepository();
			addRepoToClose(clonedSub1);
			assertNotNull(clonedSub1);
			assertEquals(new File(git2.getRepository().getWorkTree()
					walk.getPath())
			assertEquals(
					new File(new File(git2.getRepository().getDirectory()
							"modules")
					clonedSub1.getDirectory());
		}
	}

	@Test
	public void testCloneRepositoryWithNestedSubmodules() throws Exception {
		git.checkout().setName(Constants.MASTER).call();

		File submodule1 = createTempDirectory("testCloneRepositoryWithNestedSubmodules1");
		Git sub1Git = Git.init().setDirectory(submodule1).call();
		assertNotNull(sub1Git);
		Repository sub1 = sub1Git.getRepository();
		assertNotNull(sub1);
		addRepoToClose(sub1);

		String file = "file.txt";
		String path = "sub";

		write(new File(sub1.getWorkTree()
		sub1Git.add().addFilepattern(file).call();
		RevCommit commit = sub1Git.commit().setMessage("create file").call();
		assertNotNull(commit);

		File submodule2 = createTempDirectory("testCloneRepositoryWithNestedSubmodules2");
		Git sub2Git = Git.init().setDirectory(submodule2).call();
		assertNotNull(sub2Git);
		Repository sub2 = sub2Git.getRepository();
		assertNotNull(sub2);
		addRepoToClose(sub2);

		write(new File(sub2.getWorkTree()
		sub2Git.add().addFilepattern(file).call();
		RevCommit sub2Head = sub2Git.commit().setMessage("create file").call();
		assertNotNull(sub2Head);

		Repository r = sub1Git.submoduleAdd().setPath(path)
				.setURI(sub2.getDirectory().toURI().toString()).call();
		assertNotNull(r);
		addRepoToClose(r);
		RevCommit sub1Head = sub1Git.commit().setAll(true)
				.setMessage("Adding submodule").call();
		assertNotNull(sub1Head);

		r = git.submoduleAdd().setPath(path)
				.setURI(sub1.getDirectory().toURI().toString()).call();
		assertNotNull(r);
		addRepoToClose(r);
		assertNotNull(git.commit().setAll(true).setMessage("Adding submodule")
				.call());

		File directory = createTempDirectory("testCloneRepositoryWithNestedSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(true);
		clone.setURI(git.getRepository().getDirectory().toURI().toString());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER
		assertTrue(new File(git2.getRepository().getWorkTree()
				+ File.separatorChar + file).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				+ File.separatorChar + path + File.separatorChar + file)
				.exists());

		SubmoduleStatusCommand status = new SubmoduleStatusCommand(
				git2.getRepository());
		Map<String
		SubmoduleStatus pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED
		assertEquals(sub1Head
		assertEquals(sub1Head

		SubmoduleWalk walk = SubmoduleWalk.forIndex(git2.getRepository());
		assertTrue(walk.next());
		try (Repository clonedSub1 = walk.getRepository()) {
			assertNotNull(clonedSub1);
			assertEquals(new File(git2.getRepository().getWorkTree()
					walk.getPath())
			assertEquals(
					new File(new File(git2.getRepository().getDirectory()
							"modules")
					clonedSub1.getDirectory());
			status = new SubmoduleStatusCommand(clonedSub1);
			statuses = status.call();
		}
		pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED
		assertEquals(sub2Head
		assertEquals(sub2Head
		assertFalse(walk.next());
	}

	@Test
	public void testCloneWithAutoSetupRebase() throws Exception {
		File directory = createTempDirectory("testCloneRepository1");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNull(git2.getRepository().getConfig().getEnum(
				BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE

		FileBasedConfig userConfig = SystemReader.getInstance().openUserConfig(
				null
		userConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE
				ConfigConstants.CONFIG_KEY_ALWAYS);
		userConfig.save();
		directory = createTempDirectory("testCloneRepository2");
		command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(BranchRebaseMode.REBASE
				git2.getRepository().getConfig().getEnum(
						BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));

		userConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE
				ConfigConstants.CONFIG_KEY_REMOTE);
		userConfig.save();
		directory = createTempDirectory("testCloneRepository2");
		command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(BranchRebaseMode.REBASE
				git2.getRepository().getConfig().getEnum(
						BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));

	}

	private String fileUri() {
	}
}
