package org.eclipse.jgit.blame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileTreeEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CopyModifiedSearchStrategyTest extends RepositoryTestCase {
	private static final String ENCODING = "UTF-8";

	public void testPerfectMatch() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String filenames[] = new String[] { "first"
		String versions[] = new String[] { a
		ObjectId lastCommitId = null;
		ObjectInserter inserter = repo.getObjectDatabase().newInserter();
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		int i = 0;
		for (String version : versions) {
			ObjectId id = inserter.insert(Constants.OBJ_BLOB
					version.getBytes(ENCODING));
			final Tree tree = new Tree(repo);
			tree.addEntry(new FileTreeEntry(tree
					.getBytes(ENCODING)
			ObjectId treeId = inserter
					.insert(Constants.OBJ_TREE

			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(""
			commit.setCommitter(new PersonIdent(""
			commit.setMessage("test");
			commit.setTreeId(treeId);
			if (lastCommitId != null) {
				commit.setParentIds(new ObjectId[] { lastCommitId });
			}
			ObjectId commitId = inserter.insert(commit);
			commitMap.put(filenames[i]
			lastCommitId = commitId;
			commitIds.add(commitId);
			i++;
		}
		RevWalk revWalk = new RevWalk(repo);
		RevCommit latestCommit = revWalk.parseCommit(lastCommitId);
		CopyModifiedSearchStrategy strategy = new CopyModifiedSearchStrategy();
		Origin[] origins = strategy.findOrigins(new Origin(repo
				"second"));
		List<Origin> actual = Arrays.asList(origins);
		RevCommit firstCommit = revWalk.parseCommit(commitMap.get("first"));
		List<Origin> expected = Arrays.asList(new Origin(repo
				"first"));
		assertEquals(expected
	}

	public void testSimilar() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = "b 1\n";
		String filenames[] = new String[] { "first"
		String versions[] = new String[] { a
		ObjectId lastCommitId = null;
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		ObjectInserter inserter = repo.getObjectDatabase().newInserter();
		int i = 0;
		for (String version : versions) {
			ObjectId id = inserter.insert(Constants.OBJ_BLOB
					version.getBytes(ENCODING));
			final Tree tree = new Tree(repo);
			tree.addEntry(new FileTreeEntry(tree
					.getBytes(ENCODING)
			ObjectId treeId = inserter
					.insert(Constants.OBJ_TREE
			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(""
			commit.setCommitter(new PersonIdent(""
			commit.setMessage("test");
			commit.setTreeId(treeId);
			if (lastCommitId != null) {
				commit.setParentIds(new ObjectId[] { lastCommitId });
			}
			ObjectId commitId = inserter.insert(commit);
			commitMap.put(filenames[i]
			lastCommitId = commitId;
			commitIds.add(commitId);
			i++;
		}
		RevWalk revWalk = new RevWalk(repo);
		RevCommit latestCommit = revWalk.parseCommit(lastCommitId);
		CopyModifiedSearchStrategy strategy = new CopyModifiedSearchStrategy();
		Origin[] origins = strategy.findOrigins(new Origin(repo
				"second"));
		List<Origin> actual = Arrays.asList(origins);
		RevCommit firstCommit = revWalk.parseCommit(commitMap.get("first"));
		List<Origin> expected = Arrays.asList(new Origin(repo
				"first"));
		assertEquals(expected
	}

	public void testTwoSimilar() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = "b 1\n";
		String c = "c 1\n";
		String x = "x 1\n";
		String filenames[][] = new String[][] {
				new String[] { "firstb"
		String versions[][] = new String[][] { new String[] { a + b
				new String[] { a + x } };
		ObjectId lastCommitId = null;
		ObjectInserter inserter = repo.getObjectDatabase().newInserter();
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		int i = 0;
		for (String[] version : versions) {
			final Tree tree = new Tree(repo);
			int j = 0;
			for (String file : version) {
				ObjectId id = inserter.insert(Constants.OBJ_BLOB
						file.getBytes(ENCODING));
				tree.addEntry(new FileTreeEntry(tree
						.getBytes(ENCODING)
				j++;
			}
			ObjectId treeId = inserter
					.insert(Constants.OBJ_TREE

			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(""
			commit.setCommitter(new PersonIdent(""
			commit.setMessage("test");
			commit.setTreeId(treeId);
			if (lastCommitId != null) {
				commit.setParentIds(new ObjectId[] { lastCommitId });
			}
			ObjectId commitId = inserter.insert(commit);
			commitMap.put(filenames[i][0]
			lastCommitId = commitId;
			commitIds.add(commitId);
			i++;
		}
		RevWalk revWalk = new RevWalk(repo);
		RevCommit latestCommit = revWalk.parseCommit(lastCommitId);
		CopyModifiedSearchStrategy strategy = new CopyModifiedSearchStrategy();
		Origin[] origins = strategy.findOrigins(new Origin(repo
				"second"));
		List<Origin> actual = Arrays.asList(origins);
		RevCommit firstCommit = revWalk.parseCommit(commitMap.get("firstb"));
		List<Origin> expected = Arrays.asList(new Origin(repo
				"firstb")
		assertEquals(expected
	}

	public void testUnsimilar() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = "b 1\nb 2\nb 3\n";
		String filenames[] = new String[] { "first"
		String versions[] = new String[] { a
		ObjectId lastCommitId = null;
		ObjectInserter inserter = repo.getObjectDatabase().newInserter();
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		int i = 0;
		for (String version : versions) {
			ObjectId id = inserter.insert(Constants.OBJ_BLOB
					version.getBytes(ENCODING));
			final Tree tree = new Tree(repo);
			tree.addEntry(new FileTreeEntry(tree
					.getBytes(ENCODING)
			ObjectId treeId = inserter
					.insert(Constants.OBJ_TREE

			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(""
			commit.setCommitter(new PersonIdent(""
			commit.setMessage("test");
			commit.setTreeId(treeId);
			if (lastCommitId != null) {
				commit.setParentIds(new ObjectId[] { lastCommitId });
			}
			ObjectId commitId = inserter.insert(commit);
			commitMap.put(filenames[i]
			lastCommitId = commitId;
			commitIds.add(commitId);
			i++;
		}
		RevWalk revWalk = new RevWalk(repo);
		RevCommit latestCommit = revWalk.parseCommit(lastCommitId);
		CopyModifiedSearchStrategy strategy = new CopyModifiedSearchStrategy();
		Origin[] origins = strategy.findOrigins(new Origin(repo
				"second"));
		List<Origin> actual = Arrays.asList(origins);
		List<Origin> expected = new LinkedList<Origin>();
		assertEquals(expected
	}

	public void testActualCopy() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = "b 1\n";
		String filenames[][] = new String[][] { new String[] { "first" }
				new String[] { "first_copy"
		String versions[][] = new String[][] { new String[] { a }
				new String[] { a + b
		ObjectId lastCommitId = null;
		ObjectInserter inserter = repo.getObjectDatabase().newInserter();
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		int i = 0;
		for (String[] version : versions) {
			final Tree tree = new Tree(repo);
			int j = 0;
			for (String file : version) {
				ObjectId id = inserter.insert(Constants.OBJ_BLOB
						file.getBytes(ENCODING));
				tree.addEntry(new FileTreeEntry(tree
						.getBytes(ENCODING)
				j++;
			}
			ObjectId treeId = inserter
					.insert(Constants.OBJ_TREE

			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(""
			commit.setCommitter(new PersonIdent(""
			commit.setMessage("test");
			commit.setTreeId(treeId);
			if (lastCommitId != null) {
				commit.setParentIds(new ObjectId[] { lastCommitId });
			}
			ObjectId commitId = inserter.insert(commit);
			commitMap.put(filenames[i][0]
			lastCommitId = commitId;
			commitIds.add(commitId);
			i++;
		}
		RevWalk revWalk = new RevWalk(repo);
		RevCommit latestCommit = revWalk.parseCommit(lastCommitId);
		CopyModifiedSearchStrategy strategy = new CopyModifiedSearchStrategy();
		Origin[] origins = strategy.findOrigins(new Origin(repo
				"first_copy"));
		List<Origin> actual = Arrays.asList(origins);
		RevCommit firstCommit = revWalk.parseCommit(commitMap.get("first"));
		List<Origin> expected = Arrays.asList(new Origin(repo
				"first"));
		assertEquals(expected
	}

}
