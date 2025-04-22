package org.eclipse.jgit.submodule;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleDeinitCommand;
import org.eclipse.jgit.api.SubmoduleDeinitResult;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SubmoduleDeinitTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() throws GitAPIException {
		SubmoduleDeinitCommand command = new SubmoduleDeinitCommand(db);
		Collection<SubmoduleDeinitResult> modules = command.call();
		assertNotNull(modules);
		assertTrue(modules.isEmpty());
	}

	@Test
	public void alreadyClosedSubmodule() throws Exception {
		final String path = "sub";
		Git git = Git.wrap(db);

		commitSubmoduleCreation(path

		SubmoduleDeinitResult result = runDeinit(new SubmoduleDeinitCommand(db).addPath("sub"));
		assertEquals(path
		assertEquals(SubmoduleDeinitCommand.SubmoduleDeinitStatus.ALREADY_DEINITIALIZED
	}

	@Test
	public void dirtySubmoduleBecauseUntracked() throws Exception {
		final String path = "sub";
		Git git = Git.wrap(db);

		commitSubmoduleCreation(path

		Collection<String> updated = new SubmoduleUpdateCommand(db).addPath(path).setFetch(false).call();
		assertEquals(1

		File submoduleDir = assertSubmoduleIsInitialized();
		SubmoduleWalk generator;

		write(new File(submoduleDir

		SubmoduleDeinitResult result = runDeinit(new SubmoduleDeinitCommand(db).addPath("sub"));
		assertEquals(path
		assertEquals(SubmoduleDeinitCommand.SubmoduleDeinitStatus.DIRTY

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertTrue(submoduleDir.isDirectory());
		assertNotEquals(0
	}

	@Test
	public void dirtySubmoduleBecauseNewCommit() throws Exception {
		final String path = "sub";
		Git git = Git.wrap(db);

		commitSubmoduleCreation(path

		Collection<String> updated = new SubmoduleUpdateCommand(db).addPath(path).setFetch(false).call();
		assertEquals(1

		File submoduleDir = assertSubmoduleIsInitialized();
		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		generator.next();

		try (Repository submoduleLocalRepo = generator.getRepository()) {
			JGitTestUtil.writeTrashFile(submoduleLocalRepo
					"new data");
			Git.wrap(submoduleLocalRepo).commit().setAll(true)
					.setMessage("local commit").call();
		}
		SubmoduleDeinitResult result = runDeinit(new SubmoduleDeinitCommand(db).addPath("sub"));
		assertEquals(path
		assertEquals(SubmoduleDeinitCommand.SubmoduleDeinitStatus.DIRTY

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertTrue(submoduleDir.isDirectory());
		assertNotEquals(0
	}

	private File assertSubmoduleIsInitialized() throws IOException {
		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		File submoduleDir = new File(db.getWorkTree()
		assertTrue(submoduleDir.isDirectory());
		assertNotEquals(0
		return submoduleDir;
	}

	@Test
	public void dirtySubmoduleWithForce() throws Exception {
		final String path = "sub";
		Git git = Git.wrap(db);

		commitSubmoduleCreation(path

		Collection<String> updated = new SubmoduleUpdateCommand(db).addPath(path).setFetch(false).call();
		assertEquals(1

		File submoduleDir = assertSubmoduleIsInitialized();

		write(new File(submoduleDir

		SubmoduleDeinitCommand command = new SubmoduleDeinitCommand(db).addPath("sub").setForce(true);
		SubmoduleDeinitResult result = runDeinit(command);
		assertEquals(path
		assertEquals(SubmoduleDeinitCommand.SubmoduleDeinitStatus.FORCED

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertTrue(submoduleDir.isDirectory());
		assertEquals(0
	}

	@Test
	public void cleanSubmodule() throws Exception {
		final String path = "sub";
		Git git = Git.wrap(db);

		commitSubmoduleCreation(path

		Collection<String> updated = new SubmoduleUpdateCommand(db).addPath(path).setFetch(false).call();
		assertEquals(1

		File submoduleDir = assertSubmoduleIsInitialized();

		SubmoduleDeinitResult result = runDeinit(new SubmoduleDeinitCommand(db).addPath("sub"));
		assertEquals(path
		assertEquals(SubmoduleDeinitCommand.SubmoduleDeinitStatus.SUCCESS

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertTrue(submoduleDir.isDirectory());
		assertEquals(0
	}

	private SubmoduleDeinitResult runDeinit(SubmoduleDeinitCommand command) throws GitAPIException {
		Collection<SubmoduleDeinitResult> deinitialized = command.call();
		assertNotNull(deinitialized);
		assertEquals(1
		return deinitialized.iterator().next();
	}


	private RevCommit commitSubmoduleCreation(String path
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		final RevCommit commit = git.commit().setMessage("create file").call();

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(commit);
			}
		});
		editor.commit();

		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
						.toString());
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.save();

		new File(db.getWorkTree()
		git.add().addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("create submodule").call();
		return commit;
	}
}
