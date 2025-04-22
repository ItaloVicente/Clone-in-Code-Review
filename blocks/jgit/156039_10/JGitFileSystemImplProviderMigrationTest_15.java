package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.jgit.niofs.fs.options.MergeCopyOption;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderMergeTest extends AbstractTestInfra {

	@Test
	public void testMergeSuccessful() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}


		provider.copy(master

		{

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		provider.copy(userBranch

		final Git gitRepo = ((JGitFileSystem) master.getFileSystem()).getGit();

		final List<DiffEntry> result = new ListDiffs(gitRepo
				new GetTreeFromRef(gitRepo

		assertThat(result.size()).isEqualTo(0);
	}

	@Test(expected = GitException.class)
	public void testMergeConflicts() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}


		provider.copy(master

		{

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content changed".getBytes());
			outStream.close();
		}
		{

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}
		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my very cool content".getBytes());
			outStream.close();
		}

		provider.copy(userBranch
	}

	@Test
	public void testMergeBinarySuccessful() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage("images/drools.png"));
			outStream.close();
		}


		provider.copy(master

		{

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write(this.loadImage("images/jbpm.png"));
			outStream2.close();
		}
		{

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write(this.loadImage("images/opta.png"));
			outStream3.close();
		}

		provider.copy(userBranch

		final Git gitRepo = ((JGitFileSystem) master.getFileSystem()).getGit();
		final List<DiffEntry> result = new ListDiffs(gitRepo
				new GetTreeFromRef(gitRepo

		assertThat(result.size()).isEqualTo(0);
	}

	@Test(expected = GitException.class)
	public void testBinaryMergeConflicts() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage("images/drools.png"));
			outStream.close();
		}


		provider.copy(master

		{

			final OutputStream outStream = provider.newOutputStream(path2);
			outStream.write(this.loadImage("images/jbpm.png"));
			outStream.close();
		}
		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage("images/jbpm.png"));
			outStream.close();
		}
		{

			final OutputStream outStream = provider.newOutputStream(path3);
			outStream.write(this.loadImage("images/opta.png"));
			outStream.close();
		}
		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage(""));
			outStream.close();
		}

		provider.copy(userBranch
	}

	@Test(expected = GitException.class)
	public void testTryToMergeNonexistentBranch() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}


		provider.copy(develop
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingParemeter() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}


		provider.copy(develop
	}
}
