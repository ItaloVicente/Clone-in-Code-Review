/*******************************************************************************
 * Copyright (C) 2017, Thomas Wolf <thomas.wolf@paranor.ch>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.core.internal.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.test.GitTestCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for reading blobs from a commit with .gitattributes support.
 */
public class CommitFileRevisionTest extends GitTestCase {

	Repository repository;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = FileRepositoryBuilder.create(gitDir);
		repository.create();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		repository.close();
		super.tearDown();
	}

	private java.nio.file.Path createFile(IProject base, String name,
			String content) throws IOException {
		java.nio.file.Path path = base.getLocation().toFile().toPath()
				.resolve(name);
		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
		return path;
	}

	private RevCommit setupFilter(Repository repo, IProject base,
			boolean commit) throws Exception {
		createFile(base, ".gitattributes", "*.txt filter=test");
		FilterCommandRegistry.register(builtinCommandName,
				new TestCommandFactory('a', 'x'));
		StoredConfig config = repo.getConfig();
		config.setString("filter", "test", "smudge", builtinCommandName);
		config.save();
		if (commit) {
			try (Git git = new Git(repo)) {
				git.add().addFilepattern(".").call();
				return git.commit().setMessage("Add .gitattributes").call();
			}
		}
		return null;
	}

	@Test
	public void testWithAttributesCheckedIn() throws Exception {
		java.nio.file.Path filePath = createFile(project.getProject(),
				"attr.txt", "a");
		try (Git git = new Git(repository)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Initial commit").call();
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			List<String> content = Files.readAllLines(filePath,
					StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("a", content.get(0));
			RevCommit head = setupFilter(repository, project.getProject(),
					true);
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			content = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("x", content.get(0));
			String relativePath = repository.getWorkTree().toPath()
					.relativize(filePath).toString();
			CommitFileRevision fileRevision = new CommitFileRevision(repository,
					head, relativePath);
			ByteBuffer rawContent = null;
			try (InputStream blobStream = fileRevision
					.getStorage(new NullProgressMonitor()).getContents()) {
				rawContent = IO.readWholeStream(blobStream, 1);
			}
			assertNotNull(rawContent);
			String blobContent = new String(rawContent.array(), 0,
					rawContent.limit(), StandardCharsets.UTF_8);
			assertEquals("x", blobContent);
		} finally {
		}
	}

	@Test
	public void testWithAttributesNotCheckedIn() throws Exception {
		java.nio.file.Path filePath = createFile(project.getProject(),
				"attr.txt", "a");
		try (Git git = new Git(repository)) {
			git.add().addFilepattern(".").call();
			RevCommit head = git.commit().setMessage("Initial commit").call();
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			List<String> content = Files.readAllLines(filePath,
					StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("a", content.get(0));
			setupFilter(repository, project.getProject(), false);
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			content = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("x", content.get(0));
			String relativePath = repository.getWorkTree().toPath()
					.relativize(filePath).toString();
			CommitFileRevision fileRevision = new CommitFileRevision(repository,
					head, relativePath);
			ByteBuffer rawContent = null;
			try (InputStream blobStream = fileRevision
					.getStorage(new NullProgressMonitor()).getContents()) {
				rawContent = IO.readWholeStream(blobStream, 1);
			}
			assertNotNull(rawContent);
			String blobContent = new String(rawContent.array(), 0,
					rawContent.limit(), StandardCharsets.UTF_8);
			assertEquals("a", blobContent);
		} finally {
		}
	}

	@Test
	public void testWithAttributesNotCheckedInButWithGlobalAttributes()
			throws Exception {
		java.nio.file.Path filePath = createFile(project.getProject(),
				"attr.txt", "a");
		try (Git git = new Git(repository)) {
			git.add().addFilepattern(".").call();
			RevCommit head = git.commit().setMessage("Initial commit").call();
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			List<String> content = Files.readAllLines(filePath,
					StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("a", content.get(0));
			setupFilter(repository, project.getProject(), false);
			java.nio.file.Path globalAttributes = createFile(
					project.getProject(), "globalattrs", "*.txt filter=test2");
					new TestCommandFactory('a', 'y'));
			StoredConfig config = repository.getConfig();
			config.setString("core", null, "attributesFile",
					globalAttributes.toString());
			config.setString("filter", "test2", "smudge",
			config.save();
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			content = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("x", content.get(0));
			String relativePath = repository.getWorkTree().toPath()
					.relativize(filePath).toString();
			CommitFileRevision fileRevision = new CommitFileRevision(repository,
					head, relativePath);
			ByteBuffer rawContent = null;
			try (InputStream blobStream = fileRevision
					.getStorage(new NullProgressMonitor()).getContents()) {
				rawContent = IO.readWholeStream(blobStream, 1);
			}
			assertNotNull(rawContent);
			String blobContent = new String(rawContent.array(), 0,
					rawContent.limit(), StandardCharsets.UTF_8);
			assertEquals("y", blobContent);
		} finally {
		}
	}

	@Test
	public void testWithAttributesCheckedInAndWithGlobalAttributes()
			throws Exception {
		java.nio.file.Path filePath = createFile(project.getProject(),
				"attr.txt", "a");
		try (Git git = new Git(repository)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Initial commit").call();
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			List<String> content = Files.readAllLines(filePath,
					StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("a", content.get(0));
			RevCommit head = setupFilter(repository, project.getProject(),
					true);
			java.nio.file.Path globalAttributes = createFile(
					project.getProject(), "globalattrs", "*.txt filter=test2");
					new TestCommandFactory('a', 'y'));
			StoredConfig config = repository.getConfig();
			config.setString("core", null, "attributesFile",
					globalAttributes.toString());
			config.setString("filter", "test2", "smudge",
			config.save();
			createFile(project.getProject(), "attr.txt", "aa");
			git.reset().setMode(ResetType.HARD).call();
			content = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			assertEquals(1, content.size());
			assertEquals("x", content.get(0));
			String relativePath = repository.getWorkTree().toPath()
					.relativize(filePath).toString();
			CommitFileRevision fileRevision = new CommitFileRevision(repository,
					head, relativePath);
			ByteBuffer rawContent = null;
			try (InputStream blobStream = fileRevision
					.getStorage(new NullProgressMonitor()).getContents()) {
				rawContent = IO.readWholeStream(blobStream, 1);
			}
			assertNotNull(rawContent);
			String blobContent = new String(rawContent.array(), 0,
					rawContent.limit(), StandardCharsets.UTF_8);
			assertEquals("x", blobContent);
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
