package org.eclipse.jgit.gitrepo;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.gitrepo.RepoCommand.RemoteFile;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.BlobBasedConfig;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.junit.Test;

public class RepoCommandTest extends RepositoryTestCase {

	private static final String BRANCH = "branch";
	private static final String TAG = "release";

	private Repository defaultDb;
	private Repository notDefaultDb;
	private Repository groupADb;
	private Repository groupBDb;

	private String rootUri;
	private String defaultUri;
	private String notDefaultUri;
	private String groupAUri;
	private String groupBUri;

	private ObjectId oldCommitId;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		defaultDb = createWorkRepository();
		try (Git git = new Git(defaultDb)) {
			JGitTestUtil.writeTrashFile(defaultDb
			git.add().addFilepattern("hello.txt").call();
			oldCommitId = git.commit().setMessage("Initial commit").call().getId();
			git.checkout().setName(BRANCH).setCreateBranch(true).call();
			git.checkout().setName("master").call();
			git.tag().setName(TAG).call();
			JGitTestUtil.writeTrashFile(defaultDb
			git.add().addFilepattern("hello.txt").call();
			git.commit().setMessage("Second commit").call();
			addRepoToClose(defaultDb);
		}

		notDefaultDb = createWorkRepository();
		try (Git git = new Git(notDefaultDb)) {
			JGitTestUtil.writeTrashFile(notDefaultDb
			git.add().addFilepattern("world.txt").call();
			git.commit().setMessage("Initial commit").call();
			addRepoToClose(notDefaultDb);
		}

		groupADb = createWorkRepository();
		try (Git git = new Git(groupADb)) {
			JGitTestUtil.writeTrashFile(groupADb
			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("Initial commit").call();
			addRepoToClose(groupADb);
		}

		groupBDb = createWorkRepository();
		try (Git git = new Git(groupBDb)) {
			JGitTestUtil.writeTrashFile(groupBDb
			git.add().addFilepattern("b.txt").call();
			git.commit().setMessage("Initial commit").call();
			addRepoToClose(groupBDb);
		}

		resolveRelativeUris();
	}

	static class IndexedRepos implements RepoCommand.RemoteReader {
		Map<String

		IndexedRepos() {
			uriRepoMap = new HashMap<>();
		}

		void put(String u
			uriRepoMap.put(u
		}

		@Override
		public ObjectId sha1(String uri
			if (!uriRepoMap.containsKey(uri)) {
				return null;
			}

			Repository r = uriRepoMap.get(uri);
			try {
				Ref ref = r.findRef(refname);
				if (ref == null) return null;

				ref = r.getRefDatabase().peel(ref);
				ObjectId id = ref.getObjectId();
				return id;
			} catch (IOException e) {
				throw new InvalidRemoteException(""
			}
		}

		@Override
		public RemoteFile readFileWithMode(String uri
				throws GitAPIException
			Repository repo = uriRepoMap.get(uri);
			ObjectId refCommitId = sha1(uri
			if (refCommitId == null) {
				throw new InvalidRefNameException(MessageFormat
						.format(JGitText.get().refNotResolved
			}
			RevCommit commit = repo.parseCommit(refCommitId);
			TreeWalk tw = TreeWalk.forPath(repo

			return new RemoteFile(tw.getObjectReader().open(tw.getObjectId(0))
					.getCachedBytes(Integer.MAX_VALUE)
		}
	}

	private Repository cloneRepository(Repository repo
			throws Exception {
		Repository r = Git.cloneRepository()
				.setURI(repo.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true)).setBare(bare).call()
				.getRepository();
		if (bare) {
			assertTrue(r.isBare());
		} else {
			assertFalse(r.isBare());
		}
		return r;
	}

	private static void assertContents(Path path
			throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path
			String content = reader.readLine();
			assertEquals("Unexpected content in " + path.getFileName()
					expected
		}
	}

	@Test
	public void runTwiceIsNOP() throws Exception {
		try (Repository child = cloneRepository(groupADb
				Repository dest = cloneRepository(db
			StringBuilder xmlContent = new StringBuilder();
			xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\"..\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"base\" name=\"platform/base\" />")
					.append("</manifest>");
			RepoCommand cmd = new RepoCommand(dest);

			IndexedRepos repos = new IndexedRepos();
			repos.put("platform/base"

			RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(UTF_8)))
					.setRemoteReader(repos).setURI("platform/")
					.setTargetURI("platform/superproject")
					.setRecordRemoteBranch(true).setRecordSubmoduleLabels(true)
					.call();

			String firstIdStr = commit.getId().name() + ":" + ".gitmodules";
			commit = new RepoCommand(dest)
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(UTF_8)))
					.setRemoteReader(repos).setURI("platform/")
					.setTargetURI("platform/superproject")
					.setRecordRemoteBranch(true).setRecordSubmoduleLabels(true)
					.call();
			String idStr = commit.getId().name() + ":" + ".gitmodules";
			assertEquals(firstIdStr
		}
	}

	@Test
	public void androidSetup() throws Exception {
		try (Repository child = cloneRepository(groupADb
				Repository dest = cloneRepository(db
			StringBuilder xmlContent = new StringBuilder();
			xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\"..\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"base\" name=\"platform/base\" />")
					.append("</manifest>");
			RepoCommand cmd = new RepoCommand(dest);

			IndexedRepos repos = new IndexedRepos();
			repos.put("platform/base"

			RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(UTF_8)))
					.setRemoteReader(repos).setURI("platform/")
					.setTargetURI("platform/superproject")
					.setRecordRemoteBranch(true).setRecordSubmoduleLabels(true)
					.call();

			String idStr = commit.getId().name() + ":" + ".gitmodules";
			ObjectId modId = dest.resolve(idStr);

			try (ObjectReader reader = dest.newObjectReader()) {
				byte[] bytes = reader.open(modId)
						.getCachedBytes(Integer.MAX_VALUE);
				Config base = new Config();
				BlobBasedConfig cfg = new BlobBasedConfig(base
				String subUrl = cfg.getString("submodule"
						"url");
				assertEquals(subUrl
			}
		}
	}

	@Test
	public void recordUnreachableRemotes() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"base\" name=\"platform/base\" />")
			.append("</manifest>");

		try (Repository dest = cloneRepository(db
			RevCommit commit = new RepoCommand(dest)
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(UTF_8)))
					.setRemoteReader(new IndexedRepos()).setURI("platform/")
					.setTargetURI("platform/superproject")
					.setRecordRemoteBranch(true).setIgnoreRemoteFailures(true)
					.setRecordSubmoduleLabels(true).call();

			String idStr = commit.getId().name() + ":" + ".gitmodules";
			ObjectId modId = dest.resolve(idStr);

			try (ObjectReader reader = dest.newObjectReader()) {
				byte[] bytes = reader.open(modId)
						.getCachedBytes(Integer.MAX_VALUE);
				Config base = new Config();
				BlobBasedConfig cfg = new BlobBasedConfig(base
				String subUrl = cfg.getString("submodule"
						"url");
				assertEquals(subUrl
			}
		}
	}

	@Test
	public void gerritSetup() throws Exception {
		try (Repository child = cloneRepository(groupADb
				Repository dest = cloneRepository(db
			StringBuilder xmlContent = new StringBuilder();
			xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"plugins/cookbook\" name=\"plugins/cookbook\" />")
					.append("</manifest>");
			RepoCommand cmd = new RepoCommand(dest);

			IndexedRepos repos = new IndexedRepos();
			repos.put("plugins/cookbook"

			RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(UTF_8)))
					.setRemoteReader(repos).setURI("").setTargetURI("gerrit")
					.setRecordRemoteBranch(true).setRecordSubmoduleLabels(true)
					.call();

			String idStr = commit.getId().name() + ":" + ".gitmodules";
			ObjectId modId = dest.resolve(idStr);

			try (ObjectReader reader = dest.newObjectReader()) {
				byte[] bytes = reader.open(modId)
						.getCachedBytes(Integer.MAX_VALUE);
				Config base = new Config();
				BlobBasedConfig cfg = new BlobBasedConfig(base
				String subUrl = cfg.getString("submodule"
						"url");
				assertEquals(subUrl
			}
		}
	}

	@Test
	public void absoluteRemoteURL() throws Exception {
		try (Repository child = cloneRepository(groupADb
				Repository dest = cloneRepository(db
			boolean fetchSlash = false;
			boolean baseSlash = false;
			do {
				do {
					String fetchUrl = fetchSlash ? abs + "/" : abs;
					String baseUrl = baseSlash ? abs + "/" : abs;

					StringBuilder xmlContent = new StringBuilder();
					xmlContent.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
							.append("<manifest>")
							.append("<remote name=\"origin\" fetch=\""
									+ fetchUrl + "\" />")
							.append("<default revision=\"master\" remote=\"origin\" />")
							.append("<project path=\"src\" name=\"chromium/src\" />")
							.append("</manifest>");
					RepoCommand cmd = new RepoCommand(dest);

					IndexedRepos repos = new IndexedRepos();
					repos.put(repoUrl

					RevCommit commit = cmd
							.setInputStream(new ByteArrayInputStream(
									xmlContent.toString().getBytes(UTF_8)))
							.setRemoteReader(repos).setURI(baseUrl)
							.setTargetURI("gerrit").setRecordRemoteBranch(true)
							.setRecordSubmoduleLabels(true).call();

					String idStr = commit.getId().name() + ":" + ".gitmodules";
					ObjectId modId = dest.resolve(idStr);

					try (ObjectReader reader = dest.newObjectReader()) {
						byte[] bytes = reader.open(modId)
								.getCachedBytes(Integer.MAX_VALUE);
						Config base = new Config();
						BlobBasedConfig cfg = new BlobBasedConfig(base
						String subUrl = cfg.getString("submodule"
								"chromium/src"
						assertEquals(
								subUrl);
					}
					fetchSlash = !fetchSlash;
				} while (fetchSlash);
				baseSlash = !baseSlash;
			} while (baseSlash);
		}
	}

	@Test
	public void absoluteRemoteURLAbsoluteTargetURL() throws Exception {
		try (Repository child = cloneRepository(groupADb
				Repository dest = cloneRepository(db
			boolean fetchSlash = false;
			boolean baseSlash = false;
			do {
				do {
					String fetchUrl = fetchSlash ? abs + "/" : abs;
					String baseUrl = baseSlash ? abs + "/" : abs;

					StringBuilder xmlContent = new StringBuilder();
					xmlContent.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
							.append("<manifest>")
							.append("<remote name=\"origin\" fetch=\""
									+ fetchUrl + "\" />")
							.append("<default revision=\"master\" remote=\"origin\" />")
							.append("<project path=\"src\" name=\"chromium/src\" />")
							.append("</manifest>");
					RepoCommand cmd = new RepoCommand(dest);

					IndexedRepos repos = new IndexedRepos();
					repos.put(repoUrl

					RevCommit commit = cmd
							.setInputStream(new ByteArrayInputStream(
									xmlContent.toString().getBytes(UTF_8)))
							.setRemoteReader(repos).setURI(baseUrl)
							.setTargetURI(abs + "/superproject")
							.setRecordRemoteBranch(true)
							.setRecordSubmoduleLabels(true).call();

					String idStr = commit.getId().name() + ":" + ".gitmodules";
					ObjectId modId = dest.resolve(idStr);

					try (ObjectReader reader = dest.newObjectReader()) {
						byte[] bytes = reader.open(modId)
								.getCachedBytes(Integer.MAX_VALUE);
						Config base = new Config();
						BlobBasedConfig cfg = new BlobBasedConfig(base
						String subUrl = cfg.getString("submodule"
								"chromium/src"
						assertEquals("../chromium/src"
					}
					fetchSlash = !fetchSlash;
				} while (fetchSlash);
				baseSlash = !baseSlash;
			} while (baseSlash);
		}
	}

	@Test
	public void testAddRepoManifest() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		assertTrue("submodule should be checked out"
		assertContents(hello.toPath()
	}

	@Test
	public void testRepoManifestGroups() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" groups=\"a
			.append("<project path=\"bar\" name=\"")
			.append(notDefaultUri)
			.append("\" groups=\"notdefault\" />")
			.append("<project path=\"a\" name=\"")
			.append(groupAUri)
			.append("\" groups=\"a\" />")
			.append("<project path=\"b\" name=\"")
			.append(groupBUri)
			.append("\" groups=\"b\" />")
			.append("</manifest>");

		Repository localDb = createWorkRepository();
		JGitTestUtil.writeTrashFile(
				localDb
		RepoCommand command = new RepoCommand(localDb);
		command
			.setPath(localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File file = new File(localDb.getWorkTree()
		assertTrue("default should have foo"
		file = new File(localDb.getWorkTree()
		assertFalse("default shouldn't have bar"
		file = new File(localDb.getWorkTree()
		assertTrue("default should have a"
		file = new File(localDb.getWorkTree()
		assertTrue("default should have b"

		localDb = createWorkRepository();
		JGitTestUtil.writeTrashFile(
				localDb
		command = new RepoCommand(localDb);
		command
			.setPath(localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.setGroups("all
			.call();
		file = new File(localDb.getWorkTree()
		assertFalse("\"all
		file = new File(localDb.getWorkTree()
		assertTrue("\"all
		file = new File(localDb.getWorkTree()
		assertFalse("\"all
		file = new File(localDb.getWorkTree()
		assertTrue("\"all
	}

	@Test
	public void testRepoManifestCopyFile() throws Exception {
		Repository localDb = createWorkRepository();
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\">")
			.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
			.append("</project>")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(
				localDb
		RepoCommand command = new RepoCommand(localDb);
		command
			.setPath(localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(localDb.getWorkTree()
		assertTrue("The original file should exist"
		assertFalse("The original file should not be executable"
				hello.canExecute());
		assertContents(hello.toPath()
		hello = new File(localDb.getWorkTree()
		assertTrue("The destination file should exist"
		assertFalse("The destination file should not be executable"
				hello.canExecute());
		assertContents(hello.toPath()
	}

	@Test
	public void testRepoManifestCopyFile_executable() throws Exception {
		try (Git git = new Git(defaultDb)) {
			git.checkout().setName("master").call();
			File f = JGitTestUtil.writeTrashFile(defaultDb
					"content of the executable file");
			f.setExecutable(true);
			git.add().addFilepattern("hello.sh").call();
			git.commit().setMessage("Add binary file").call();
		}

		Repository localDb = createWorkRepository();
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\">")
				.append("<copyfile src=\"hello.sh\" dest=\"copy-hello.sh\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(localDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(localDb);
		command.setPath(
				localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();

		File hello = new File(localDb.getWorkTree()
		assertTrue("The original file should exist"
		assertTrue("The original file must be executable"
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("The original file should have expected content"
					"content of the executable file"
		}

		hello = new File(localDb.getWorkTree()
		assertTrue("The destination file should exist"
		assertTrue("The destination file must be executable"
				hello.canExecute());
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("The destination file should have expected content"
					"content of the executable file"
		}
	}

	@Test
	public void testBareRepo() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			try (BufferedReader reader = Files
					.newBufferedReader(gitmodules.toPath()
				String content = reader.readLine();
				assertEquals(
						"The first line of .gitmodules file should be as expected"
						"[submodule \"" + defaultUri + "\"]"
			}
			String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
			String remote = defaultDb.resolve(Constants.HEAD).name();
			assertEquals("The gitlink should be the same as remote head"
					remote
		}
	}

	@Test
	public void testRevision() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" revision=\"")
			.append(oldCommitId.name())
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("submodule content should be as expected"
					"branch world"
		}
	}

	@Test
	public void testRevisionBranch() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"")
			.append(BRANCH)
			.append("\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		assertContents(hello.toPath()
	}

	@Test
	public void testRevisionTag() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" revision=\"")
			.append(TAG)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		assertContents(hello.toPath()
	}

	@Test
	public void testRevisionBare() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"").append(BRANCH)
				.append("\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testRevisionBare");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
			assertEquals("The gitlink is same as remote head"
					oldCommitId.name()
		}
	}

	@Test
	public void testCopyFileBare() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" revision=\"").append(BRANCH).append("\" >")
				.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
				.append("<copyfile src=\"hello.txt\" dest=\"foo/Hello\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testCopyFileBare");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			File hello = new File(localDb.getWorkTree()
			assertTrue("The Hello file should exist"
			File foohello = new File(localDb.getWorkTree()
			assertFalse("The foo/Hello file should be skipped"
					foohello.exists());
			assertContents(hello.toPath()
		}
	}

	@Test
	public void testCopyFileBare_executable() throws Exception {
		try (Git git = new Git(defaultDb)) {
			git.checkout().setName(BRANCH).call();
			File f = JGitTestUtil.writeTrashFile(defaultDb
					"content of the executable file");
			f.setExecutable(true);
			git.add().addFilepattern("hello.sh").call();
			git.commit().setMessage("Add binary file").call();
		}

		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" revision=\"").append(BRANCH)
				.append("\" >")
				.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
				.append("<copyfile src=\"hello.txt\" dest=\"foo/Hello\" />")
				.append("<copyfile src=\"hello.sh\" dest=\"copy-hello.sh\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testCopyFileBare");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			File hello = new File(localDb.getWorkTree()
			assertTrue("The Hello file should exist"
			File foohello = new File(localDb.getWorkTree()
			assertFalse("The foo/Hello file should be skipped"
					foohello.exists());
			try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
					UTF_8)) {
				String content = reader.readLine();
				assertEquals("The Hello file should have expected content"
						"branch world"
			}

			File helloSh = new File(localDb.getWorkTree()
			assertTrue("Destination file should exist"
			assertContents(helloSh.toPath()
			assertTrue("Destination file should be executable"
					helloSh.canExecute());

		}
	}

	@Test
	public void testReplaceManifestBare() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" revision=\"").append(BRANCH).append("\" >")
				.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/old.xml")
				.setURI(rootUri).call();
		xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"bar\" name=\"").append(notDefaultUri)
				.append("\" >")
				.append("<copyfile src=\"world.txt\" dest=\"World.txt\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
		command = new RepoCommand(remoteDb);
		command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/new.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testReplaceManifestBare");
		File dotmodules;
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			File hello = new File(localDb.getWorkTree()
			assertFalse("The Hello file shouldn't exist"
			File hellotxt = new File(localDb.getWorkTree()
			assertTrue("The World.txt file should exist"
			dotmodules = new File(localDb.getWorkTree()
					Constants.DOT_GIT_MODULES);
		}
		try (BufferedReader reader = Files
				.newBufferedReader(dotmodules.toPath()
			boolean foo = false;
			boolean bar = false;
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				if (line.contains("submodule \"" + defaultUri + "\""))
					foo = true;
				if (line.contains("submodule \"" + notDefaultUri + "\""))
					bar = true;
			}
			assertTrue("The bar submodule should exist"
			assertFalse("The foo submodule shouldn't exist"
		}
	}

	@Test
	public void testRemoveOverlappingBare() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo/bar\" name=\"").append(groupBUri)
				.append("\" />").append("<project path=\"a\" name=\"")
				.append(groupAUri).append("\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testRemoveOverlappingBare");
		File dotmodules;
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			dotmodules = new File(localDb.getWorkTree()
				Constants.DOT_GIT_MODULES);
		}

		try (BufferedReader reader = Files
				.newBufferedReader(dotmodules.toPath()
			boolean foo = false;
			boolean foobar = false;
			boolean a = false;
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				if (line.contains("submodule \"" + defaultUri + "\""))
					foo = true;
				if (line.contains("submodule \"" + groupBUri + "\""))
					foobar = true;
				if (line.contains("submodule \"" + groupAUri + "\""))
					a = true;
			}
			assertTrue("The " + defaultUri + " submodule should exist"
			assertFalse("The " + groupBUri + " submodule shouldn't exist"
					foobar);
			assertTrue("The " + groupAUri + " submodule should exist"
		}
	}

	@Test
	public void testIncludeTag() throws Exception {
		Repository localDb = createWorkRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<include name=\"_include.xml\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(
				tempDb

		xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		JGitTestUtil.writeTrashFile(
				tempDb

		RepoCommand command = new RepoCommand(localDb);
		command
			.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(localDb.getWorkTree()
		assertTrue("submodule should be checked out"
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("submodule content should be as expected"
					"master world"
		}
	}
	@Test
	public void testRemoteAlias() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" alias=\"remote2\" />")
			.append("<default revision=\"master\" remote=\"remote2\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");

		Repository localDb = createWorkRepository();
		JGitTestUtil.writeTrashFile(
				localDb
		RepoCommand command = new RepoCommand(localDb);
		command
			.setPath(localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File file = new File(localDb.getWorkTree()
		assertTrue("We should have foo"
	}

	@Test
	public void testTargetBranch() throws Exception {
		Repository remoteDb1 = createBareRepository();
		Repository remoteDb2 = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb1);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setTargetBranch("test").call();
		ObjectId branchId = remoteDb1
				.resolve(Constants.R_HEADS + "test^{tree}");
		command = new RepoCommand(remoteDb2);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		ObjectId defaultId = remoteDb2.resolve(Constants.HEAD + "^{tree}");
		assertEquals(
				"The tree id of branch db and default db should be the same"
				branchId
	}

	@Test
	public void testRecordRemoteBranch() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"with-branch\" ")
				.append("revision=\"master\" ").append("name=\"")
				.append(notDefaultUri).append("\" />")
				.append("<project path=\"with-long-branch\" ")
				.append("revision=\"refs/heads/master\" ").append("name=\"")
				.append(defaultUri).append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecordRemoteBranch(true).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository();) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			FileBasedConfig c = new FileBasedConfig(gitmodules
			c.load();
			assertEquals(
					"Recording remote branches should work for short branch descriptions"
					"master"
					c.getString("submodule"
			assertEquals(
					"Recording remote branches should work for full ref specs"
					"refs/heads/master"
					c.getString("submodule"
		}
	}


	@Test
	public void testRecordSubmoduleLabels() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"test\" ")
				.append("revision=\"master\" ").append("name=\"")
				.append(notDefaultUri).append("\" ")
				.append("groups=\"a1
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecordSubmoduleLabels(true).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository();) {
			File gitattributes = new File(localDb.getWorkTree()
					".gitattributes");
			assertTrue("The .gitattributes file should exist"
					gitattributes.exists());
			try (BufferedReader reader = Files
					.newBufferedReader(gitattributes.toPath()
					UTF_8)) {
				String content = reader.readLine();
				assertEquals(".gitattributes content should be as expected"
						"/test a1 a2"
			}
		}
	}

	@Test
	public void testRecordShallowRecommendation() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"shallow-please\" ").append("name=\"")
				.append(defaultUri).append("\" ").append("clone-depth=\"1\" />")
				.append("<project path=\"non-shallow\" ").append("name=\"")
				.append(notDefaultUri).append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecommendShallow(true).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository();) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			FileBasedConfig c = new FileBasedConfig(gitmodules
			c.load();
			assertEquals("Recording shallow configuration should work"
					c.getString("submodule"
			assertNull("Recording non shallow configuration should work"
					c.getString("submodule"
		}
	}

	@Test
	public void testRemoteRevision() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<remote name=\"remote2\" fetch=\".\" revision=\"")
			.append(BRANCH)
			.append("\" />")
			.append("<default remote=\"remote1\" revision=\"master\" />")
			.append("<project path=\"foo\" remote=\"remote2\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		assertContents(hello.toPath()
	}

	@Test
	public void testDefaultRemoteRevision() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" revision=\"")
			.append(BRANCH)
			.append("\" />")
			.append("<default remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree().getAbsolutePath() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File hello = new File(db.getWorkTree()
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("submodule content should be as expected"
					"branch world"
		}
	}

	@Test
	public void testTwoPathUseTheSameName() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"path1\" ").append("name=\"")
				.append(defaultUri).append("\" />")
				.append("<project path=\"path2\" ").append("name=\"")
				.append(defaultUri).append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecommendShallow(true).call();
		File directory = createTempDirectory("testBareRepo");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository();) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			FileBasedConfig c = new FileBasedConfig(gitmodules
			c.load();
			assertEquals("A module should exist for path1"
					c.getString("submodule"
			assertEquals("A module should exist for path2"
					c.getString("submodule"
		}
	}

	private void resolveRelativeUris() {
		defaultUri = defaultDb.getDirectory().toURI().toString();
		notDefaultUri = notDefaultDb.getDirectory().toURI().toString();
		groupAUri = groupADb.getDirectory().toURI().toString();
		groupBUri = groupBDb.getDirectory().toURI().toString();
		int start = 0;
		while (start <= defaultUri.length()) {
			int newStart = defaultUri.indexOf('/'
			String prefix = defaultUri.substring(0
			if (!notDefaultUri.startsWith(prefix) ||
					!groupAUri.startsWith(prefix) ||
					!groupBUri.startsWith(prefix)) {
				start++;
				rootUri = defaultUri.substring(0
				defaultUri = defaultUri.substring(start);
				notDefaultUri = notDefaultUri.substring(start);
				groupAUri = groupAUri.substring(start);
				groupBUri = groupBUri.substring(start);
				return;
			}
			start = newStart;
		}
	}

	void testRelative(String a
		String got = RepoCommand.relativize(URI.create(a)

		if (!got.equals(want)) {
			fail(String.format("relative('%s'
		}
	}

	@Test
	public void relative() {
		testRelative("a/b/"
		testRelative("a/b"
		testRelative("a/"
		testRelative("a/"
		testRelative("/a/b/c"
		testRelative("/abc"
		testRelative("abc"
		testRelative("abc"
	}
}
