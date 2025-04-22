package org.eclipse.egit.core.synchronize;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.eclipse.egit.core.synchronize.CheckedInCommitsCache.Change;
import org.eclipse.jgit.api.Git;
import org.junit.Test;

@SuppressWarnings("boxing")
public class StagedChangeCacheTest extends AbstractCacheTest {

	@Test
	public void shouldListSingleWorkspaceAddition() throws Exception {
		writeTrashFile("a.txt", "trash");
		new Git(db).add().addFilepattern("a.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileAddition(result, "a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceAdditions() throws Exception {
		writeTrashFile("a.txt", "trash");
		writeTrashFile("b.txt", "trash");
		new Git(db).add().addFilepattern("a.txt").addFilepattern("b.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileAddition(result, "a.txt", "a.txt");
		assertFileAddition(result, "b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceAdditionInFolder() throws Exception {
		writeTrashFile("folder/a.txt", "trash");
		new Git(db).add().addFilepattern("folder/a.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileAddition(result, "folder/a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceAdditionsInFolder() throws Exception {
		writeTrashFile("folder/a.txt", "trash");
		writeTrashFile("folder/b.txt", "trash");
		new Git(db).add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileAddition(result, "folder/a.txt", "a.txt");
		assertFileAddition(result, "folder/b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceDeletion() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a.txt", "trash");
		git.add().addFilepattern("a.txt").call();
		git.commit().setMessage("initial add").call();
		git.rm().addFilepattern("a.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileDeletion(result, "a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceDeletions() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a.txt", "trash");
		writeTrashFile("b.txt", "trash");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		git.commit().setMessage("new commit").call();
		git.rm().addFilepattern("a.txt").addFilepattern("b.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileDeletion(result, "a.txt", "a.txt");
		assertFileDeletion(result, "b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceDeletionInFolder() throws Exception {
		Git git = new Git(db);
		writeTrashFile("folder/a.txt", "trash");
		git.add().addFilepattern("folder/a.txt").call();
		git.commit().setMessage("new commit").call();
		git.rm().addFilepattern("folder/a.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileDeletion(result, "folder/a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceDeletionsInFolder() throws Exception {
		Git git = new Git(db);
		writeTrashFile("folder/a.txt", "trash");
		writeTrashFile("folder/b.txt", "trash");
		git.add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();
		git.commit().setMessage("new commit").call();
		git.rm().addFilepattern("folder/a.txt").call();
		git.rm().addFilepattern("folder/b.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileDeletion(result, "folder/a.txt", "a.txt");
		assertFileDeletion(result, "folder/b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceChange() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a.txt", "trash");
		git.add().addFilepattern("a.txt").call();
		git.commit().setMessage("initial a.txt commit").call();
		writeTrashFile("a.txt", "modification");
		git.add().addFilepattern("a.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileChange(result, "a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceChanges() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a.txt", "trash");
		writeTrashFile("b.txt", "trash");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		git.commit().setMessage("new commmit").call();
		writeTrashFile("a.txt", "modification");
		writeTrashFile("b.txt", "modification");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileChange(result, "a.txt", "a.txt");
		assertFileChange(result, "b.txt", "b.txt");
	}

	@Test
	public void shouldListSingleWorkspaceChangeInFolder() throws Exception {
		Git git = new Git(db);
		writeTrashFile("folder/a.txt", "trash");
		git.add().addFilepattern("folder/a.txt").call();
		git.commit().setMessage("new commit").call();
		writeTrashFile("folder/a.txt", "modification");
		git.add().addFilepattern("folder/a.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(1));
		assertFileChange(result, "folder/a.txt", "a.txt");
	}

	@Test
	public void shouldListTwoWorkspaceChagneInFolder() throws Exception {
		Git git = new Git(db);
		writeTrashFile("folder/a.txt", "trash");
		writeTrashFile("folder/b.txt", "trash");
		git.add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();
		git.commit().setMessage("new commit").call();
		writeTrashFile("folder/a.txt", "modification");
		writeTrashFile("folder/b.txt", "modification");
		git.add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();

		Map<String, Change> result = StagedChangeCache.build(db);

		assertThat(result.size(), is(2));
		assertFileChange(result, "folder/a.txt", "a.txt");
		assertFileChange(result, "folder/b.txt", "b.txt");
	}

}
