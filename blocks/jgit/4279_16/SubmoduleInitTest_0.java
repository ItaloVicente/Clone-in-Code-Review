package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class SubmoduleGeneratorTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() throws IOException
			ConfigInvalidException {
		SubmoduleGenerator gen = new SubmoduleGenerator(db);
		assertFalse(gen.next());
		assertNull(gen.getPath());
		assertNull(gen.getObjectId());
		assertFalse(gen.hasGitDirectory());
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getDirectory());
		assertNull(gen.getDirectory());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		assertNull(gen.getRepository());
	}

	@Test
	public void repositoryWithOneSubmodule() throws IOException
			ConfigInvalidException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleGenerator gen = new SubmoduleGenerator(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertEquals(new File(db.getWorkTree()
				+ Constants.DOT_GIT)
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}
}
