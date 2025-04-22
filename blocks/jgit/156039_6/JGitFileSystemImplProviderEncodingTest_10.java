package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.fs.attribute.BranchDiff;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderDiffTest extends AbstractTestInfra {

	private Logger logger = LoggerFactory.getLogger(JGitFileSystemImplProviderDiffTest.class);

	@Test
	public void testDiffsBetweenBranches() throws IOException {

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();
		final Repository gitRepo = origin.getRepository();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
								tempFile("temp1\ntemp1\ntemp3\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\nother\n"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
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


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		provider.newFileSystem(newRepo

		final Path path = provider.getPath(newRepo);
		final BranchDiff branchDiff = (BranchDiff) provider.readAttributes(path

		branchDiff.diffs().forEach(elem -> logger.info(elem.toString()));

		assertThat(branchDiff.diffs().size()).isEqualTo(5);
	}

	@Test
	public void testBranchesDoNotHaveDifferences() throws IOException {

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder
		final Git origin = new CreateRepository(gitSource).execute().get();
		final Repository gitRepo = origin.getRepository();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
								tempFile("temp1\ntemp1\ntemp3\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\nother\n"));
					}
				}).execute();

		new Commit(origin
				new HashMap<String
					{
						put("file1.txt"
					}
				}).execute();

		new CreateBranch((GitImpl) origin


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		provider.newFileSystem(newRepo

		final Path path = provider.getPath(newRepo);
		final BranchDiff branchDiff = (BranchDiff) provider.readAttributes(path

		branchDiff.diffs().forEach(elem -> logger.info(elem.toString()));

		assertThat(branchDiff.diffs().size()).isEqualTo(0);
	}
}
