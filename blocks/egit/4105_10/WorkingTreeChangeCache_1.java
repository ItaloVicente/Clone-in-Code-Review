package org.eclipse.egit.core.synchronize;

import static org.eclipse.compare.structuremergeviewer.Differencer.ADDITION;
import static org.eclipse.compare.structuremergeviewer.Differencer.CHANGE;
import static org.eclipse.compare.structuremergeviewer.Differencer.DELETION;
import static org.eclipse.compare.structuremergeviewer.Differencer.RIGHT;
import static org.eclipse.jgit.junit.JGitTestUtil.deleteTrashFile;
import static org.eclipse.jgit.junit.JGitTestUtil.writeTrashFile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.jgit.api.Git;
import org.junit.Test;

@SuppressWarnings("boxing")
public class WorkingTreeChangeCacheTest extends AbstractCacheTest {

	@Test
	public void shouldListSingleWorkspaceAddition() throws Exception {
		writeTrashFile(db, "a.txt", "trash");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileAddition(result, "a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceAdditions() throws Exception {
		writeTrashFile(db, "a.txt", "trash");
		writeTrashFile(db, "b.txt", "trash");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileAddition(result, "a.txt", "a.txt");
		assertFileAddition(result, "b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceAdditionInFolder() throws Exception {
		writeTrashFile(db, "folder/a.txt", "trash");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileAddition(result, "folder/a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceAdditionsInFolder() throws Exception {
		writeTrashFile(db, "folder/a.txt", "trash");
		writeTrashFile(db, "folder/b.txt", "trash");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileAddition(result, "folder/a.txt", "a.txt");
		assertFileAddition(result, "folder/b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceDeletion() throws Exception {
		writeTrashFile(db, "a.txt", "trash");
		new Git(db).add().addFilepattern("a.txt").call();
		deleteTrashFile(db, "a.txt");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileDeletion(result, "a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceDeletions() throws Exception {
		writeTrashFile(db, "a.txt", "trash");
		writeTrashFile(db, "b.txt", "trash");
		new Git(db).add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		deleteTrashFile(db, "a.txt");
		deleteTrashFile(db, "b.txt");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileDeletion(result, "a.txt", "a.txt");
		assertFileDeletion(result, "b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceDeletionInFolder() throws Exception {
		writeTrashFile(db, "folder/a.txt", "trash");
		new Git(db).add().addFilepattern("folder/a.txt").call();
		deleteTrashFile(db, "folder/a.txt");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileDeletion(result, "folder/a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceDeletionsInFolder() throws Exception {
		writeTrashFile(db, "folder/a.txt", "trash");
		writeTrashFile(db, "folder/b.txt", "trash");
		new Git(db).add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();
		deleteTrashFile(db, "folder/a.txt");
		deleteTrashFile(db, "folder/b.txt");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileDeletion(result, "folder/a.txt", "a.txt");
		assertFileDeletion(result, "folder/b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceChange() throws Exception {
		writeTrashFile(db, "a.txt", "trash");
		new Git(db).add().addFilepattern("a.txt").call();
		writeTrashFile(db, "a.txt", "modification");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileChange(result, "a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceChanges() throws Exception {
		writeTrashFile(db, "a.txt", "trash");
		writeTrashFile(db, "b.txt", "trash");
		new Git(db).add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		writeTrashFile(db, "a.txt", "modification");
		writeTrashFile(db, "b.txt", "modification");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileChange(result, "a.txt", "a.txt");
		assertFileChange(result, "b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceChangeInFolder() throws Exception {
		writeTrashFile(db, "folder/a.txt", "trash");
		new Git(db).add().addFilepattern("folder/a.txt").call();
		writeTrashFile(db, "folder/a.txt", "modification");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileChange(result, "folder/a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceChagneInFolder() throws Exception {
		writeTrashFile(db, "folder/a.txt", "trash");
		writeTrashFile(db, "folder/b.txt", "trash");
		new Git(db).add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();
		writeTrashFile(db, "folder/a.txt", "modification");
		writeTrashFile(db, "folder/b.txt", "modification");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileChange(result, "folder/a.txt", "a.txt");
		assertFileChange(result, "folder/b.txt", "b.txt");
	}

	@Test
	public void shouldNotListIgnorefFile() throws Exception {
		writeTrashFile(db, "a.txt", "content");
		writeTrashFile(db, ".gitignore", "a.txt");

		Map<String, Change> result = WorkingTreeChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileAddition(result, ".gitignore", ".gitignore");
	}

	private void assertFileAddition(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(RIGHT | ADDITION));
		assertThat(result.get(path).getObjectId(), not(ZERO_ID));
		assertNull(result.get(path).getRemoteObjectId());
	}

	private void assertFileDeletion(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(RIGHT | DELETION));
		assertThat(result.get(path).getRemoteObjectId(), not(ZERO_ID));
		assertNull(result.get(path).getObjectId());
	}

	private void assertFileChange(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(RIGHT | CHANGE));
		assertThat(result.get(path).getObjectId(), not(ZERO_ID));
		assertThat(result.get(path).getRemoteObjectId(), not(ZERO_ID));
	}

	private void commonFileAsserts(Map<String, Change> result, String path,
			String fileName) {
		assertTrue(result.containsKey(path));
		assertThat(result.get(path).getName(), is(fileName));
	}

}
