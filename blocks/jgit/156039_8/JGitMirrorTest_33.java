package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
import org.eclipse.jgit.niofs.internal.op.commands.Merge;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitMergeTest extends AbstractTestInfra {

	private static final String SOURCE_GIT = "source/source";

	@Test
	public void testMergeFastForwardSuccessful() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file4.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file5.txt"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeNonFastForwardSuccessful() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file4.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file5.txt"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeNoDiff() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		List<String> commitIds = new Merge(origin

		assertThat(commitIds).isEmpty();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParametersNotNull() throws IOException {

		new Merge(null
	}

	@Test(expected = GitException.class)
	public void testTryToMergeNonexistentBranch() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file4.txt"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file5.txt"
					}
				}).execute();

		new Merge(origin
	}

	@Test(expected = GitException.class)
	public void testMergeBinaryInformationButHasConflicts() throws IOException {

		final byte[] contentA = this.loadImage("images/drools.png");
		final byte[] contentB = this.loadImage("images/jbpm.png");
		final byte[] contentC = this.loadImage("images/opta.png");

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeBinaryInformationSuccessful() throws IOException {

		final byte[] contentA = this.loadImage("images/drools.png");
		final byte[] contentB = this.loadImage("images/jbpm.png");

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.jpg"
					}
				}).execute();

		new Merge(origin

		final List<DiffEntry> result = new ListDiffs(origin
				new GetTreeFromRef(origin

		assertThat(result).isEmpty();
	}
}
