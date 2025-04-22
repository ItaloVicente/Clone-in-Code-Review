/*******************************************************************************
 * Copyright (C) 2017, Thomas Wolf <thomas.wolf@paranor.ch>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.ui.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.ResetOperation;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.egit.ui.internal.revision.EditableRevision;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.IO;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for CompareUtils; in particular editing index content as it can be done
 * in the compare editor.
 */
public class CompareUtilsTest extends LocalRepositoryTestCase {

	private Repository repository;

	@Before
	public void setup() throws Exception {
		File repoFile = createProjectAndCommitToRepository();
		assertNotNull(repoFile);
		repository = Activator.getDefault().getRepositoryCache()
				.lookupRepository(repoFile);
		assertNotNull(repository);
	}

	private String get(InputStream in) throws IOException {
		ByteBuffer buffer = IO.readWholeStream(in, 1);
		return new String(buffer.array(), 0, buffer.limit(),
				StandardCharsets.UTF_8);
	}

	@Test
	public void testIndexEdit() throws Exception {
		IFile testFile = touch("a");
		stage(testFile);
		ITypedElement element = CompareUtils.getIndexTypedElement(testFile);
		assert (element instanceof EditableRevision);
		EditableRevision revision = (EditableRevision) element;
		try (InputStream in = revision.getContents()) {
			assertEquals("a", get(in));
		}
		revision.setContent("xx".getBytes(StandardCharsets.UTF_8));
		CommitOperation op = new CommitOperation(repository,
				TestUtil.TESTAUTHOR, TestUtil.TESTCOMMITTER,
				"Commit modified index");
		op.execute(null);
		TestUtil.waitForJobs(50, 5000);
		ResetOperation reset = new ResetOperation(repository, Constants.HEAD,
				ResetType.HARD);
		reset.execute(null);
		TestUtil.waitForJobs(50, 5000);
		try (InputStream in = testFile.getContents()) {
			assertEquals("xx", get(in));
		}
	}

	@Test
	public void testIndexEditExecutable() throws Exception {
		assumeTrue(repository.getFS().supportsExecute());
		IFile testFile = touch("a");
		File rawFile = new File(testFile.getLocation().toOSString());
		repository.getFS().setExecute(rawFile, true);
		testFile.refreshLocal(IResource.DEPTH_ZERO, null);
		stage(testFile);
		assertEquals("Executable bit should be set", FileMode.EXECUTABLE_FILE,
				getIndexEntryMode(FILE1_PATH));
		ITypedElement element = CompareUtils.getIndexTypedElement(testFile);
		assert (element instanceof EditableRevision);
		EditableRevision revision = (EditableRevision) element;
		try (InputStream in = revision.getContents()) {
			assertEquals("a", get(in));
		}
		revision.setContent("xx".getBytes(StandardCharsets.UTF_8));
		assertEquals("Executable bit should be set", FileMode.EXECUTABLE_FILE,
				getIndexEntryMode(FILE1_PATH));
	}

	private FileMode getIndexEntryMode(String path) throws Exception {
		DirCache dc = repository.readDirCache();
		try (TreeWalk w = new TreeWalk(repository)) {
			w.addTree(new DirCacheIterator(dc));
			w.setFilter(PathFilterGroup.createFromStrings(path));
			w.setRecursive(true);
			assertTrue(path + " not in index", w.next());
			return w.getFileMode();
		}
	}

	@Test
	public void testIndexEditWithAttributes() throws Exception {
		IFile testFile = touch("a");
		stage(testFile);
		IFile gitAttributes = touch(PROJ1, FOLDER + "/.gitattributes",
				FILE1 + " filter=test");
		try {
					new TestCommandFactory('a', 'x'));
			StoredConfig config = repository.getConfig();
			config.setString("filter", "test", "smudge",
			config.save();
			ITypedElement element = CompareUtils.getIndexTypedElement(testFile);
			assert (element instanceof EditableRevision);
			EditableRevision revision = (EditableRevision) element;
			try (InputStream in = revision.getContents()) {
				assertEquals("x", get(in));
			}
					new TestCommandFactory('x', 'a'));
			config.setString("filter", "test", "clean",
			config.save();
			revision.setContent("xx".getBytes(StandardCharsets.UTF_8));
			CommitOperation op = new CommitOperation(repository,
					TestUtil.TESTAUTHOR, TestUtil.TESTCOMMITTER,
					"Commit modified index");
			op.execute(null);
			TestUtil.waitForJobs(50, 5000);
			gitAttributes.delete(true, true, new NullProgressMonitor());
			config.unsetSection("filter", "test");
			config.save();
			ResetOperation reset = new ResetOperation(repository,
					Constants.HEAD, ResetType.HARD);
			reset.execute(null);
			TestUtil.waitForJobs(50, 5000);
			try (InputStream in = testFile.getContents()) {
				assertEquals("aa", get(in));
			}
		} finally {
		}
	}

	private static class TestCommandFactory implements FilterCommandFactory {
		private final int toReplace;

		private final int replacement;

		public TestCommandFactory(int toReplace, int replacement) {
			this.toReplace = toReplace;
			this.replacement = replacement;
		}

		@Override
		public FilterCommand create(Repository repo, InputStream in,
				final OutputStream out) {
			FilterCommand cmd = new FilterCommand(in, out) {

				@Override
				public int run() throws IOException {
					int b = in.read();
					if (b == -1) {
						return b;
					} else if (b == toReplace) {
						out.write(replacement);
					} else {
						out.write(b);
					}
					return 1;
				}
			};
			return cmd;
		}
	}
}
