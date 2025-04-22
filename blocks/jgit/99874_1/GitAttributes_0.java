package org.eclipse.jgit.attributes.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.function.Consumer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.junit.Ignore;
import org.junit.Test;

public class MergeGitAttributeTest extends RepositoryTestCase {

	private static final String REFS_HEADS_RIGHT = "refs/heads/right";

	private static final String REFS_HEADS_MASTER = "refs/heads/master";

	private static final String REFS_HEADS_LEFT = "refs/heads/left";

	private static final String DISABLE_CHECK_BRANCH = "refs/heads/disabled_checked";

	private static final String ENABLE_CHECKED_BRANCH = "refs/heads/enabled_checked";

	private static final String ENABLED_CHECKED_GIF = "enabled_checked.gif";

	public Git createRepositoryBinaryConflit(Consumer<Git> initialCommit
			Consumer<Git> leftCommit
			throws NoFilepatternException
			IOException {
		Git git = new Git(db);

		initialCommit.accept(git);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setAll(true)
				.setMessage("initial commit adding git attribute file").call();

		createBranch(firstCommit
		checkoutBranch(REFS_HEADS_LEFT);
		leftCommit.accept(git);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Left").call();

		checkoutBranch(REFS_HEADS_MASTER);
		createBranch(firstCommit
		checkoutBranch(REFS_HEADS_RIGHT);
		rightCommit.accept(git);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Right").call();

		checkoutBranch(REFS_HEADS_LEFT);
		return git;

	}

	@Test
	public void mergeTextualFile_NoAttr() throws NoWorkTreeException
			NoFilepatternException
		try (Git git = createRepositoryBinaryConflit(g -> {
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		})) {
			checkoutBranch(REFS_HEADS_LEFT);

			MergeResult mergeResult = git.merge()
					.include(git.getRepository().resolve(REFS_HEADS_RIGHT))
					.call();
			assertEquals(MergeStatus.MERGED

			assertNull(mergeResult.getConflicts());

			String result = read(
					writeTrashFile("res.cat"
			assertEquals(result
					.resolve("main.cat").toFile()));
		}
	}

	@Test
	public void mergeTextualFile_UnsetMerge_Conflict()
			throws NoWorkTreeException
			IOException {
		try (Git git = createRepositoryBinaryConflit(g -> {
			try {
				writeTrashFile(".gitattributes"
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		})) {
			assertAddMergeAttributeUnset(REFS_HEADS_LEFT
			assertAddMergeAttributeUnset(REFS_HEADS_RIGHT

			checkoutBranch(REFS_HEADS_LEFT);

			String catContent = read(git.getRepository().getWorkTree().toPath()
					.resolve("main.cat").toFile());

			MergeResult mergeResult = git.merge()
					.include(git.getRepository().resolve(REFS_HEADS_RIGHT))
					.call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals(catContent
					.toPath().resolve("main.cat").toFile()));
		}
	}

	@Test
	public void mergeTextualFile_UnsetMerge_NoConflict()
			throws NoWorkTreeException
			IOException {
		try (Git git = createRepositoryBinaryConflit(g -> {
			try {
				writeTrashFile(".gitattributes"
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		})) {
			assertAddMergeAttributeUndefined(REFS_HEADS_LEFT
			assertAddMergeAttributeUndefined(REFS_HEADS_RIGHT

			checkoutBranch(REFS_HEADS_LEFT);

			MergeResult mergeResult = git.merge()
					.include(git.getRepository().resolve(REFS_HEADS_RIGHT))
					.call();
			assertEquals(MergeStatus.MERGED

			String result = read(
					writeTrashFile("res.cat"
			assertEquals(result
					.toPath().resolve("main.cat").toFile()));
		}
	}

	@Test
	public void mergeTextualFile_SetBinaryMerge_Conflict()
			throws NoWorkTreeException
			IOException {
		try (Git git = createRepositoryBinaryConflit(g -> {
			try {
				writeTrashFile(".gitattributes"
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			try {
				writeTrashFile("main.cat"
			} catch (IOException e) {
				e.printStackTrace();
			}
		})) {
			assertAddMergeAttributeCustom(REFS_HEADS_LEFT
					"binary");
			assertAddMergeAttributeCustom(REFS_HEADS_RIGHT
					"binary");

			checkoutBranch(REFS_HEADS_LEFT);

			String catContent = read(git.getRepository().getWorkTree().toPath()
					.resolve("main.cat").toFile());

			MergeResult mergeResult = git.merge()
					.include(git.getRepository().resolve(REFS_HEADS_RIGHT))
					.call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals(catContent
					.toPath().resolve("main.cat").toFile()));
		}
	}

	@Test
	@Ignore
	public void mergeBinaryFile_NoAttr_Conflict() throws IllegalStateException
			IOException
			CheckoutConflictException
			WrongRepositoryStateException

		RevCommit disableCheckedCommit;
		FileInputStream mergeResultFile = null;
		try (Git git = new Git(db)) {
			write(new File(db.getWorkTree()
			git.add().addFilepattern(".gitattributes").call();
			RevCommit firstCommit = git.commit()
					.setMessage("initial commit adding git attribute file")
					.call();

			createBranch(firstCommit
			checkoutBranch(ENABLE_CHECKED_BRANCH);
			copy(ENABLED_CHECKED_GIF
			git.add().addFilepattern(ENABLED_CHECKED_GIF).call();
			git.commit().setMessage("enabled_checked commit").call();

			checkoutBranch(REFS_HEADS_MASTER);
			createBranch(firstCommit
			checkoutBranch(DISABLE_CHECK_BRANCH);
			copy("disabled_checked.gif"
			git.add().addFilepattern(ENABLED_CHECKED_GIF).call();
			disableCheckedCommit = git.commit()
					.setMessage("disabled_checked commit").call();

			assertAddMergeAttributeUndefined(ENABLE_CHECKED_BRANCH
					ENABLED_CHECKED_GIF);
			assertAddMergeAttributeUndefined(DISABLE_CHECK_BRANCH
					ENABLED_CHECKED_GIF);

			checkoutBranch(ENABLE_CHECKED_BRANCH);
			MergeResult mergeResult = git.merge().include(disableCheckedCommit)
					.call();
			assertEquals(MergeStatus.CONFLICTING

			mergeResultFile = new FileInputStream(
					db.getWorkTree().toPath().resolve(ENABLED_CHECKED_GIF)
							.toFile());
			assertTrue(contentEquals(
					getClass().getResourceAsStream(ENABLED_CHECKED_GIF)
					mergeResultFile));
		} finally {
			if (mergeResultFile != null) {
				mergeResultFile.close();
			}
		}
	}

	@Test
	public void mergeBinaryFile_UnsetMerge_Conflict()
			throws IllegalStateException
			IOException
			CheckoutConflictException
			WrongRepositoryStateException

		RevCommit disableCheckedCommit;
		FileInputStream mergeResultFile = null;
		try (Git git = new Git(db)) {
			write(new File(db.getWorkTree()
			git.add().addFilepattern(".gitattributes").call();
			RevCommit firstCommit = git.commit()
					.setMessage("initial commit adding git attribute file")
					.call();

			createBranch(firstCommit
			checkoutBranch(ENABLE_CHECKED_BRANCH);
			copy(ENABLED_CHECKED_GIF
			git.add().addFilepattern(ENABLED_CHECKED_GIF).call();
			git.commit().setMessage("enabled_checked commit").call();

			checkoutBranch(REFS_HEADS_MASTER);
			createBranch(firstCommit
			checkoutBranch(DISABLE_CHECK_BRANCH);
			copy("disabled_checked.gif"
			git.add().addFilepattern(ENABLED_CHECKED_GIF).call();
			disableCheckedCommit = git.commit()
					.setMessage("disabled_checked commit").call();

			assertAddMergeAttributeUnset(ENABLE_CHECKED_BRANCH
					ENABLED_CHECKED_GIF);
			assertAddMergeAttributeUnset(DISABLE_CHECK_BRANCH
					ENABLED_CHECKED_GIF);

			checkoutBranch(ENABLE_CHECKED_BRANCH);
			MergeResult mergeResult = git.merge().include(disableCheckedCommit)
					.call();
			assertEquals(MergeStatus.CONFLICTING

			mergeResultFile = new FileInputStream(db.getWorkTree().toPath()
					.resolve(ENABLED_CHECKED_GIF).toFile());
			assertTrue(contentEquals(
					getClass().getResourceAsStream(ENABLED_CHECKED_GIF)
					mergeResultFile));
		} finally {
			if (mergeResultFile != null) {
				mergeResultFile.close();
			}
		}
	}

	@Test
	public void mergeBinaryFile_SetMerge_Conflict()
			throws IllegalStateException
			ConcurrentRefUpdateException
			InvalidMergeHeadsException
			NoMessageException

		RevCommit disableCheckedCommit;
		FileInputStream mergeResultFile = null;
		try (Git git = new Git(db)) {
			write(new File(db.getWorkTree()
			git.add().addFilepattern(".gitattributes").call();
			RevCommit firstCommit = git.commit()
					.setMessage("initial commit adding git attribute file")
					.call();

			createBranch(firstCommit
			checkoutBranch(ENABLE_CHECKED_BRANCH);
			copy(ENABLED_CHECKED_GIF
			git.add().addFilepattern(ENABLED_CHECKED_GIF).call();
			git.commit().setMessage("enabled_checked commit").call();

			checkoutBranch(REFS_HEADS_MASTER);
			createBranch(firstCommit
			checkoutBranch(DISABLE_CHECK_BRANCH);
			copy("disabled_checked.gif"
			git.add().addFilepattern(ENABLED_CHECKED_GIF).call();
			disableCheckedCommit = git.commit()
					.setMessage("disabled_checked commit").call();

			assertAddMergeAttributeSet(ENABLE_CHECKED_BRANCH
					ENABLED_CHECKED_GIF);
			assertAddMergeAttributeSet(DISABLE_CHECK_BRANCH
					ENABLED_CHECKED_GIF);

			checkoutBranch(ENABLE_CHECKED_BRANCH);
			MergeResult mergeResult = git.merge().include(disableCheckedCommit)
					.call();
			assertEquals(MergeStatus.CONFLICTING

			mergeResultFile = new FileInputStream(db.getWorkTree().toPath()
					.resolve(ENABLED_CHECKED_GIF).toFile());
			assertFalse(contentEquals(
					getClass().getResourceAsStream(ENABLED_CHECKED_GIF)
					mergeResultFile));
		} finally {
			if (mergeResultFile != null) {
				mergeResultFile.close();
			}
		}
	}

	private boolean contentEquals(InputStream input1
			throws IOException {
		if (input1 == input2) {
			return true;
		}
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			final int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}

		final int ch2 = input2.read();
		return ch2 == -1;
	}

	private void assertAddMergeAttributeUnset(String branch
			throws IllegalStateException
		checkoutBranch(branch);

		try (TreeWalk treeWaklEnableChecked = new TreeWalk(db)) {
			treeWaklEnableChecked.addTree(new FileTreeIterator(db));
			treeWaklEnableChecked.setFilter(PathFilter.create(fileName));

			assertTrue(treeWaklEnableChecked.next());
			Attributes attributes = treeWaklEnableChecked.getAttributes();
			Attribute mergeAttribute = attributes.get("merge");
			assertNotNull(mergeAttribute);
			assertEquals(Attribute.State.UNSET
		}
	}

	private void assertAddMergeAttributeSet(String branch
			throws IllegalStateException
		checkoutBranch(branch);

		try (TreeWalk treeWaklEnableChecked = new TreeWalk(db)) {
			treeWaklEnableChecked.addTree(new FileTreeIterator(db));
			treeWaklEnableChecked.setFilter(PathFilter.create(fileName));

			assertTrue(treeWaklEnableChecked.next());
			Attributes attributes = treeWaklEnableChecked.getAttributes();
			Attribute mergeAttribute = attributes.get("merge");
			assertNotNull(mergeAttribute);
			assertEquals(Attribute.State.SET
		}
	}

	private void assertAddMergeAttributeUndefined(String branch
			String fileName) throws IllegalStateException
		checkoutBranch(branch);

		try (TreeWalk treeWaklEnableChecked = new TreeWalk(db)) {
			treeWaklEnableChecked.addTree(new FileTreeIterator(db));
			treeWaklEnableChecked.setFilter(PathFilter.create(fileName));

			assertTrue(treeWaklEnableChecked.next());
			Attributes attributes = treeWaklEnableChecked.getAttributes();
			Attribute mergeAttribute = attributes.get("merge");
			assertNull(mergeAttribute);
		}
	}

	private void assertAddMergeAttributeCustom(String branch
			String value) throws IllegalStateException
		checkoutBranch(branch);

		try (TreeWalk treeWaklEnableChecked = new TreeWalk(db)) {
			treeWaklEnableChecked.addTree(new FileTreeIterator(db));
			treeWaklEnableChecked.setFilter(PathFilter.create(fileName));

			assertTrue(treeWaklEnableChecked.next());
			Attributes attributes = treeWaklEnableChecked.getAttributes();
			Attribute mergeAttribute = attributes.get("merge");
			assertNotNull(mergeAttribute);
			assertEquals(Attribute.State.CUSTOM
			assertEquals(value
		}
	}

	private void copy(String resourcePath
			String pathInRepo) throws IOException {
		InputStream input = getClass().getResourceAsStream(resourcePath);
		Files.copy(input
				.resolve(resourceNewName));
	}

}
