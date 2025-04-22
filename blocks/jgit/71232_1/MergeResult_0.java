package org.eclipse.jgit.api;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class ConflictBasedRepositoryTestCase extends RepositoryTestCase {

	protected Map<String

	private Map<String
		Map<String
		devs.put("devY"
		devs.put("devX"
		devs.put("devA"
		devs.put("devB"
		devs.put("devC"
		devs.put("devD"
		devs.put("devE"
		return devs;
	}

	public JGitMergeScenario setCollaborationScenarioInTempRepository() throws Exception {
		Git git = Git.wrap(db);

		RevCommit mergeBaseCommit

		writeTrashFile("Foo.java"
		writeTrashFile("Bar.java"
		git.add().addFilepattern("Foo.java").addFilepattern("Bar.java").call();
		git.commit().setMessage("initial commit").setAuthor(devs.get("devY")).call();

		writeTrashFile("Foo.java"
		writeTrashFile("Bar.java"
		git.add().addFilepattern("Foo.java").addFilepattern("Bar.java").call();
		mergeBaseCommit = git.commit().setMessage("m0").setAuthor(devs.get("devX")).call();

		writeTrashFile("Foo.java"
		git.add().addFilepattern("Foo.java").call();
		git.commit().setMessage("m1").setAuthor(devs.get("devE")).call();

		writeTrashFile("Foo.java"
		writeTrashFile("Bar.java"
		git.add().addFilepattern("Foo.java").addFilepattern("Bar.java").call();
		git.commit().setMessage("m2").setAuthor(devs.get("devC")).call();

		writeTrashFile("Bar.java"
		git.add().addFilepattern("Bar.java").call();
		lastMasterCommit = git.commit().setMessage("m3").setAuthor(devs.get("devB")).call();

		createBranch(mergeBaseCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("Foo.java"
		git.add().addFilepattern("Foo.java").call();
		git.commit().setMessage("s1").setAuthor(devs.get("devD")).call();

		writeTrashFile("Bar.java"
		git.add().addFilepattern("Bar.java").call();
		git.commit().setMessage("s2").setAuthor(devs.get("devE")).call();

		writeTrashFile("Bar.java"
		git.add().addFilepattern("Bar.java").call();
		lastSideCommit = git.commit().setMessage("s3").setAuthor(devs.get("devA")).call();

		return new JGitMergeScenario(mergeBaseCommit
	}

	public MergeResult runMerge(JGitMergeScenario scenario) throws RefAlreadyExistsException
			InvalidRefNameException
		Git git = Git.wrap(db);
		CheckoutCommand ckoutCmd = git.checkout();
		ckoutCmd.setName(scenario.getLeft().getName());
		ckoutCmd.setStartPoint(scenario.getLeft());
		ckoutCmd.call();

		MergeCommand mergeCmd = git.merge();
		mergeCmd.setCommit(false);
		mergeCmd.include(scenario.getRight());
		return mergeCmd.call();
	}

	public void print(MergeResult mResult) {
		Map<String
		for (String path : allConflicts.keySet()) {
			int[][] c = allConflicts.get(path);
			System.out.println("Conflicts in file " + path);
			for (int i = 0; i < c.length; ++i) {
				System.out.println("  Conflict #" + i);
				for (int j = 0; j < (c[i].length) - 1; ++j) {
					if (c[i][j] >= 0)
						System.out.println(
								"    Chunk for " + mResult.getMergedCommits()[j] + " starts on line #" + c[i][j]);
				}
			}
		}
		System.out.println("mResult.toStringExt(): "+mResult.toStringExt());
	}

	@Test
	public void buildChunckBasedNetwork() throws Exception {
		JGitMergeScenario aScenario = setCollaborationScenarioInTempRepository();

		MergeResult mr = runMerge(aScenario);

		print(mr);
	}

}

class JGitMergeScenario {
	private RevCommit left;
	private RevCommit right;

	public JGitMergeScenario(RevCommit base
		this.left = left;
		this.right = right;
	}

	public RevCommit getLeft() {
		return left;
	}

	public RevCommit getRight() {
		return right;
	}

}
