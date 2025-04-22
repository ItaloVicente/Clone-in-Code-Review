/*
 * Copyright (C) 2015, Ivan Motsch <ivan.motsch@bsiag.com>
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.CoreConfig.EOL;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.IO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

/**
 * Unit tests for end-of-line conversion and settings using core.autocrlf, *
 * core.eol and the .gitattributes eol, text, binary (macro for -diff -merge
 * -text)
 */
@RunWith(Theories.class)
public class EolRepositoryTest extends RepositoryTestCase {
	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	@DataPoint
	public static String smallContents[] = {
			generateTestData(3, 1, true, false),
			generateTestData(3, 1, false, true),
			generateTestData(3, 1, true, true) };

	@DataPoint
	public static String hugeContents[] = {
			generateTestData(1000000, 17, true, false),
			generateTestData(1000000, 17, false, true),
			generateTestData(1000000, 17, true, true) };

	static String generateTestData(int size, int lineSize, boolean withCRLF,
			boolean withLF) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0 && i % lineSize == 0) {
				if (withCRLF && withLF) {
					if (i % 2 == 0)
						sb.append("\r\n");
					else
						sb.append("\n");
				} else if (withCRLF) {
					sb.append("\r\n");
				} else if (withLF) {
					sb.append("\n");
				}
			}
			sb.append("A");
		}
		return sb.toString();
	}

	public EolRepositoryTest(String[] testContent) {
		CONTENT_CRLF = testContent[0];
		CONTENT_LF = testContent[1];
		CONTENT_MIXED = testContent[2];
	}

	protected String CONTENT_CRLF;

	protected String CONTENT_LF;

	protected String CONTENT_MIXED;

	private TreeWalk walk;

	/** work tree root .gitattributes */
	private File dotGitattributes;

	/** file containing CRLF */
	private File fileCRLF;

	/** file containing LF */
	private File fileLF;

	/** file containing mixed CRLF and LF */
	private File fileMixed;

	/** this values are set in {@link #collectRepositoryState()} */
	private static class ActualEntry {
		private String attrs;

		private String file;

		private String index;

		private int indexContentLength;
	}

	private ActualEntry entryCRLF = new ActualEntry();

	private ActualEntry entryLF = new ActualEntry();

	private ActualEntry entryMixed = new ActualEntry();

	private DirCache dc;

	@Test
	public void testDefaultSetup() throws Exception {
		setupGitAndDoHardReset(null, null, null, null, "* text=auto");
		collectRepositoryState();
		assertEquals("text=auto", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	public void checkEntryContent(ActualEntry entry, String fileContent,
			String indexContent) {
		assertEquals(fileContent, entry.file);
		assertEquals(indexContent, entry.index);
		assertEquals(fileContent.length(), entry.indexContentLength);
	}

	@Test
	public void test_ConfigAutoCRLF_false() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE, null, null, null, "* text=auto");
		collectRepositoryState();
		assertEquals("text=auto", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_true() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, null, null, null, "* text=auto");
		collectRepositoryState();
		assertEquals("text=auto", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_input() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT, null, null, null, "* text=auto");
		collectRepositoryState();
		assertEquals("text=auto", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(null, EOL.LF, "*.txt text", null, null);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigEOL_crlf() throws Exception {
		setupGitAndDoHardReset(null, EOL.CRLF, "*.txt text", null, null);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_ConfigEOL_native_windows() throws Exception {
		String origLineSeparator = System.getProperty("line.separator", "\n");
		System.setProperty("line.separator", "\r\n");
		try {
			setupGitAndDoHardReset(null, EOL.NATIVE, "*.txt text", null, null);
			collectRepositoryState();
			assertEquals("text", entryCRLF.attrs);
			checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
			checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		} finally {
			System.setProperty("line.separator", origLineSeparator);
		}
	}

	@Test
	public void test_ConfigEOL_native_xnix() throws Exception {
		String origLineSeparator = System.getProperty("line.separator", "\n");
		System.setProperty("line.separator", "\n");
		try {
			setupGitAndDoHardReset(null, EOL.NATIVE, "*.txt text", null, null);
			collectRepositoryState();
			assertEquals("text", entryCRLF.attrs);
			checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
			checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		} finally {
			System.setProperty("line.separator", origLineSeparator);
		}
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE, EOL.LF, "*.txt text", null, null);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_false_ConfigEOL_native() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE, EOL.NATIVE, "*.txt text", null, null);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_true_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, EOL.LF, "*.txt text", null, null);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_switchToBranchWithTextAttributes()
			throws Exception {
		Git git = Git.wrap(db);

		setupGitAndDoHardReset(AutoCRLF.FALSE, EOL.CRLF, null, null,
				"file1.txt text\nfile2.txt text\nfile3.txt text");
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);

		dotGitattributes = createAndAddFile(git, Constants.DOT_GIT_ATTRIBUTES,
				"file1.txt binary\nfile2.txt text\nfile3.txt text");
		gitCommit(git, "switchedToBinaryFor1");
		recreateWorktree(git);
		collectRepositoryState();
		assertEquals("binary -diff -merge -text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		assertEquals("text", entryLF.attrs);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		assertEquals("text", entryMixed.attrs);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);

		gitCheckout(git, "HEAD^");
		recreateWorktree(git);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_switchToBranchWithBinaryAttributes() throws Exception {
		Git git = Git.wrap(db);

		setupGitAndDoHardReset(AutoCRLF.FALSE, EOL.LF, null, null,
				"file1.txt binary\nfile2.txt binary\nfile3.txt binary");
		collectRepositoryState();
		assertEquals("binary -diff -merge -text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_CRLF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_MIXED, CONTENT_MIXED);

		dotGitattributes = createAndAddFile(git, Constants.DOT_GIT_ATTRIBUTES,
				"file1.txt text\nfile2.txt binary\nfile3.txt binary");
		gitCommit(git, "switchedToTextFor1");
		recreateWorktree(git);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		assertEquals("binary -diff -merge -text", entryLF.attrs);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		assertEquals("binary -diff -merge -text", entryMixed.attrs);
		checkEntryContent(entryMixed, CONTENT_MIXED, CONTENT_MIXED);

		gitCheckout(git, "HEAD^");
		recreateWorktree(git);
		collectRepositoryState();
		assertEquals("binary -diff -merge -text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_CRLF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_MIXED, CONTENT_MIXED);
	}

	@Test
	public void test_ConfigAutoCRLF_input_ConfigEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT, EOL.LF, "*.txt text", null, null);
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, EOL.LF, "*.txt eol=lf", null, null);
		collectRepositoryState();
		assertEquals("eol=lf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE, EOL.LF, "*.txt eol=lf", null, null);
		collectRepositoryState();
		assertEquals("eol=lf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_lf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT, EOL.LF, "*.txt eol=lf", null, null);
		collectRepositoryState();
		assertEquals("eol=lf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_crlf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, EOL.LF, "*.txt eol=crlf", null, null);
		collectRepositoryState();
		assertEquals("eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE, EOL.LF, "*.txt eol=crlf", null, null);
		collectRepositoryState();
		assertEquals("eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_input_GlobalEOL_crlf() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.INPUT, EOL.LF, "*.txt eol=crlf", null, null);
		collectRepositoryState();
		assertEquals("eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_true_GlobalEOL_lf_InfoEOL_crlf()
			throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, null, "*.txt eol=lf", "*.txt eol=crlf", null);
		collectRepositoryState();
		assertEquals("eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_ConfigAutoCRLF_false_GlobalEOL_crlf_InfoEOL_lf()
			throws Exception {
		setupGitAndDoHardReset(AutoCRLF.FALSE, null, "*.txt eol=crlf", "*.txt eol=lf", null);
		collectRepositoryState();
		assertEquals("eol=lf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void test_GlobalEOL_lf_RootEOL_crlf() throws Exception {
		setupGitAndDoHardReset(null, null, "*.txt eol=lf", null, "*.txt eol=crlf");
		collectRepositoryState();
		assertEquals("eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_lf() throws Exception {
		setupGitAndDoHardReset(null, null, "*.txt eol=lf", "*.txt eol=crlf", "*.txt eol=lf");
		collectRepositoryState();
		assertEquals("eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_crlf_RootEOL_unspec()
			throws Exception {
		setupGitAndDoHardReset(null, null, "*.txt eol=lf", "*.txt eol=crlf",
				"*.txt text !eol");
		collectRepositoryState();
		assertEquals("eol=crlf text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_CRLF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_CRLF, CONTENT_LF);
	}

	@Test
	public void test_GlobalEOL_lf_InfoEOL_unspec_RootEOL_crlf()
			throws Exception {
		setupGitAndDoHardReset(null, null, "*.txt eol=lf", "*.txt !eol",
				"*.txt text eol=crlf");
		collectRepositoryState();
		assertEquals("text", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_LF, CONTENT_LF);
	}

	@Test
	public void testBinary1() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, EOL.CRLF, "*.txt text", "*.txt binary",
				"*.txt eol=crlf");
		collectRepositoryState();
		assertEquals("binary -diff -merge -text eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_CRLF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_MIXED, CONTENT_MIXED);
	}

	@Test
	public void testBinary2() throws Exception {
		setupGitAndDoHardReset(AutoCRLF.TRUE, EOL.CRLF, "*.txt text eol=crlf", null,
				"*.txt binary");
		collectRepositoryState();
		assertEquals("binary -diff -merge -text eol=crlf", entryCRLF.attrs);
		checkEntryContent(entryCRLF, CONTENT_CRLF, CONTENT_CRLF);
		checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		checkEntryContent(entryMixed, CONTENT_MIXED, CONTENT_MIXED);
	}

	private void setupGitAndDoHardReset(AutoCRLF autoCRLF, EOL eol,
			String globalAttributesContent, String infoAttributesContent,
			String workDirRootAttributesContent) throws Exception {
		Git git = new Git(db);
		FileBasedConfig config = db.getConfig();
		if (autoCRLF != null) {
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_AUTOCRLF, autoCRLF);
		}
		if (eol != null) {
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_EOL, eol);
		}
		if (globalAttributesContent != null) {
			File f = new File(db.getDirectory(), "global/attrs");
			write(f, globalAttributesContent);
			config.setString(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_ATTRIBUTESFILE,
					f.getAbsolutePath());

		}
		if (infoAttributesContent != null) {
			File f = new File(db.getDirectory(), Constants.INFO_ATTRIBUTES);
			write(f, infoAttributesContent);
		}
		config.save();

		if (workDirRootAttributesContent != null) {
			dotGitattributes = createAndAddFile(git,
					Constants.DOT_GIT_ATTRIBUTES, workDirRootAttributesContent);
		} else {
			dotGitattributes = null;
		}

		fileCRLF = createAndAddFile(git, "file1.txt", CONTENT_CRLF);

		fileLF = createAndAddFile(git, "file2.txt", CONTENT_LF);

		fileMixed = createAndAddFile(git, "file3.txt", CONTENT_MIXED);

		gitCommit(git, "addFiles");

		recreateWorktree(git);
	}

	private void recreateWorktree(Git git)
			throws GitAPIException, CheckoutConflictException,
			InterruptedException, IOException, NoFilepatternException {
		for (File f : new File[] { dotGitattributes, fileCRLF, fileLF, fileMixed }) {
			if (f == null)
				continue;
			f.delete();
			Assert.assertFalse(f.exists());
		}
		gitResetHard(git);
		fsTick(db.getIndexFile());
		gitAdd(git, ".");
	}

	protected void gitCommit(Git git, String msg) throws GitAPIException {
		git.commit().setMessage(msg).call();
	}

	protected void gitAdd(Git git, String path) throws GitAPIException {
		git.add().addFilepattern(path).call();
	}

	protected void gitResetHard(Git git) throws GitAPIException {
		git.reset().setMode(ResetType.HARD).call();
	}

	protected void gitCheckout(Git git, String revstr)
			throws GitAPIException, RevisionSyntaxException, IOException {
		git.checkout().setName(db.resolve(revstr).getName()).call();
	}

	private File createAndAddFile(Git git, String path, String content)
			throws Exception {
		File f;
		int pos = path.lastIndexOf('/');
		if (pos < 0) {
			f = writeTrashFile(path, content);
		} else {
			f = writeTrashFile(path.substring(0, pos), path.substring(pos + 1),
					content);
		}
		gitAdd(git, path);
		Assert.assertTrue(f.exists());
		return f;
	}

	private void collectRepositoryState() throws Exception {
		dc = db.readDirCache();
		walk = beginWalk();
		if (dotGitattributes != null)
			collectEntryContentAndAttributes(F, ".gitattributes", null);
		collectEntryContentAndAttributes(F, fileCRLF.getName(), entryCRLF);
		collectEntryContentAndAttributes(F, fileLF.getName(), entryLF);
		collectEntryContentAndAttributes(F, fileMixed.getName(), entryMixed);
		endWalk();
	}

	private TreeWalk beginWalk() throws Exception {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		newWalk.addTree(new DirCacheIterator(db.readDirCache()));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested", walk.next());
	}

	private void collectEntryContentAndAttributes(FileMode type, String pathName,
			ActualEntry e) throws IOException {
		assertTrue("walk has entry", walk.next());

		assertEquals(pathName, walk.getPathString());
		assertEquals(type, walk.getFileMode(0));

		if (e != null) {
			e.attrs = "";
			for (Attribute a : walk.getAttributes().getAll()) {
				e.attrs += " " + a.toString();
			}
			e.attrs = e.attrs.trim();
			e.file = new String(
					IO.readFully(new File(db.getWorkTree(), pathName)));
			DirCacheEntry dce = dc.getEntry(pathName);
			ObjectLoader open = walk.getObjectReader().open(dce.getObjectId());
			e.index = new String(open.getBytes());
			e.indexContentLength = dce.getLength();
		}

		if (D.equals(type))
			walk.enterSubtree();
	}
}
