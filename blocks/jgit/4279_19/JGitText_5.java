package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class SubmoduleUpdateTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() {
		SubmoduleUpdateCommand command = new SubmoduleUpdateCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertTrue(modules.isEmpty());
	}

	@Test
	public void repositoryWithSubmodule() throws Exception {
		writeTrashFile("file.txt"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file.txt").call();
		final RevCommit commit = git.commit().setMessage("create file").call();

		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

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

		SubmoduleUpdateCommand command = new SubmoduleUpdateCommand(db);
		Collection<String> updated = command.call();
		assertNotNull(updated);
		assertEquals(1
		assertEquals(path

		SubmoduleGenerator generator = new SubmoduleGenerator(db);
		assertTrue(generator.next());
		Repository subRepo = generator.getRepository();
		assertNotNull(subRepo);
		assertEquals(commit
	}
}
