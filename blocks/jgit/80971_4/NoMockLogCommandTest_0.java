package org.eclipse.jgit.api;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class NoMockLogCommandTest {
	@DataPoints
	public static boolean[] sleep = { true

	@Theory
	public void testLogOnMergeCommit(boolean sleepBeforeMerge)
			throws Exception {
		Path folder = Files.createTempDirectory(
				"JGitTest_" + NoMockLogCommandTest.class.getName());
		Git git = null;
		try {
			git = Git.init().setBare(false).setDirectory(folder.toFile())
					.call();
			Repository repository = git.getRepository();

			git.commit().setMessage("Initial commit").call();

			git.checkout().setCreateBranch(true).setName("side").call();
			git.commit().setMessage("side1").call();

			git.checkout().setName(Constants.MASTER).call();
			git.commit().setMessage("master2").call();
			RevCommit add2 = git.commit().setMessage("master3").call();

			if (sleepBeforeMerge) {
				Thread.sleep(1000);
			}

			git.checkout().setName("side").call();
			ObjectId mergeId = git.merge()
					.include(repository.resolve(Constants.MASTER))
					.setStrategy(MergeStrategy.RECURSIVE).call().getNewHead();

			RevWalk walk = new RevWalk(repository);
			walk.markStart(walk.lookupCommit(add2.getId()));
			walk.markUninteresting(walk.lookupCommit(mergeId));
			Assert.assertNull(
					"Found an unexpected commit. sleepBeforeMerge was set to: "
							+ sleepBeforeMerge
					walk.next());
		} finally {
			if (git != null)
				git.close();
			Files.walkFileTree(folder
				@Override
				public FileVisitResult visitFile(Path file
						BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir
						IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		}
	}
}
