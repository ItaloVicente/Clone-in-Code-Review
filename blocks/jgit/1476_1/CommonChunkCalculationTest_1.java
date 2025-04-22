package org.eclipse.jgit.api;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jgit.blame.BlameEntry;
import org.eclipse.jgit.blame.Range;
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

public class BlameCommandTest extends RepositoryTestCase {

	public void testBlameEmptyPath() throws Exception {
		Git git = new Git(db);
		try {
			git.blame().call();
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testBlameNullPath() throws Exception {
		Git git = new Git(db);
		try {
			git.blame().setPath(null).call();
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testSimpleBlame() throws Exception {
		Git git = new Git(db);
		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		RevCommit commit = git.commit().setAuthor(author).setMessage("").call();
		Iterable<BlameEntry> entries = git.blame().setPath("test.txt").call();
		BlameEntry blame = entries.iterator().next();
		assertEquals(commit
		assertEquals(0
		assertEquals(2
	}

	public void testBlameCallableOnce() throws Exception {
		Git git = new Git(db);
		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		git.commit().setAuthor(author).setMessage("").call();
		BlameCommand blame = git.blame().setPath("test.txt");
		blame.call();
		try {
			blame.call();
			fail("Command should not be callable anymore");
		} catch (IllegalStateException e) {
		}
		try {
			blame.setPath("foo");
			fail("Command should not be callable anymore");
		} catch (IllegalStateException e) {
		}
	}

	public void testBlameTwoCommits() throws Exception {
		Git git = new Git(db);
		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		RevCommit firstCommit = git.commit().setAuthor(author)
				.setMessage("first")
				.call();

		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		RevCommit secondCommit = git.commit().setAuthor(author)
				.setMessage("second")
				.call();

		Iterable<BlameEntry> entries = git.blame().setPath("test.txt").call();
		BlameEntry blame = entries.iterator().next();
		Iterator<BlameEntry> iterator = entries.iterator();
		blame = iterator.next();
		assertEquals(firstCommit
		assertEquals(0
		blame = iterator.next();
		assertEquals(secondCommit
		assertEquals(1
	}

	public void testBlameStartCommit() throws Exception {
		Git git = new Git(db);
		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		RevCommit firstCommit = git.commit().setAuthor(author)
				.setMessage("first").call();

		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		RevCommit secondCommit = git.commit().setAuthor(author)
				.setMessage("second").call();

		writeTrashFile("test.txt"
		git.add().addFilepattern("test.txt").call();
		git.commit().setAuthor(author)
				.setMessage("second").call();

		Iterable<BlameEntry> entries = git.blame().setPath("test.txt")
				.setStartCommit(secondCommit).call();
		Iterator<BlameEntry> iterator = entries.iterator();
		BlameEntry blame = iterator.next();
		assertEquals(firstCommit
		assertEquals(0
		blame = iterator.next();
		assertEquals(secondCommit
		assertEquals(1
	}

	private static final String ENCODING = "UTF-8";

	public void testBlameSimple() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = a + "b 1\nb 2\nb 3";
		String c = "c 1\nc 2\nc 3\n" + b;
		String versions[] = new String[] { a
		ObjectId[] commits = new ObjectId[3];
		int i = 0;
		File file = new File(repo.getWorkTree()
		Git git = new Git(repo);
		for (String version : versions) {

			PrintWriter writer = new PrintWriter(file);
			writer.print(version);
			writer.close();

			git.add().addFilepattern("test.txt").call();
			ObjectId commitId = git.commit().setAuthor(author)
					.setCommitter(committer).setMessage("test022\n").call()
					.getId();

			commits[i] = commitId;
			i++;
		}
		ObjectId headId = repo.resolve("HEAD");
		RevCommit latestCommit = new RevWalk(repo).parseCommit(headId);

		RevWalk revWalk = new RevWalk(repo);
		RevCommit commit = revWalk.parseCommit(latestCommit.getId());
		Iterable<BlameEntry> blame = git.blame().setPath("test.txt")
				.setStartCommit(commit).call();
		Iterator<BlameEntry> iterator = blame.iterator();

		ObjectId[] expectedCommits = new ObjectId[] { commits[2]
				commits[1] };
		Range[] expectedRanges = new Range[] { new Range(0
				new Range(3
		int[] expectedSuspectStarts = new int[] { 0

		for (i = 0; i < expectedCommits.length; i++) {
			BlameEntry blameEntry = iterator.next();
			assertTrue(blameEntry.guilty);
			assertEquals(expectedCommits[i]
			assertEquals(expectedRanges[i]
			assertEquals(expectedSuspectStarts[i]
		}
	}

	public void testBlameHEAD() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = a + "b 1\nb 2\nb 3";
		String c = "c 1\nc 2\nc 3\n" + b;
		String versions[] = new String[] { a
		ObjectId[] commits = new ObjectId[3];
		int i = 0;
		File file = new File(repo.getWorkTree()
		Git git = new Git(repo);
		for (String version : versions) {

			PrintWriter writer = new PrintWriter(file);
			writer.print(version);
			writer.close();

			git.add().addFilepattern("test.txt").call();
			ObjectId commitId = git.commit().setAuthor(author)
					.setCommitter(committer).setMessage("test022\n").call()
					.getId();

			commits[i] = commitId;
			i++;
		}

		Iterable<BlameEntry> blame = git.blame().setPath("test.txt").call();
		Iterator<BlameEntry> iterator = blame.iterator();

		ObjectId[] expectedCommits = new ObjectId[] { commits[2]
				commits[1] };
		Range[] expectedRanges = new Range[] { new Range(0
				new Range(3
		int[] expectedSuspectStarts = new int[] { 0
		for (i = 0; i < expectedCommits.length; i++) {
			BlameEntry blameEntry = iterator.next();
			assertTrue(blameEntry.guilty);
			assertEquals(expectedCommits[i]
			assertEquals(expectedRanges[i]
			assertEquals(expectedSuspectStarts[i]
		}
	}

	public void testBlamePasstroughParent() throws Exception {
		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = a + "b 1\nb 2\nb 3";
		String c = "c 1\nc 2\nc 3\n" + b;
		String versions[] = new String[] { a
		ObjectId[] commits = new ObjectId[3];
		int i = 0;
		File file = new File(repo.getWorkTree()
		Git git = new Git(repo);
		for (String version : versions) {

			PrintWriter writer = new PrintWriter(file);
			writer.print(version);
			writer.close();

			git.add().addFilepattern("test.txt").call();
			ObjectId commitId = git.commit().setAuthor(author)
					.setCommitter(committer).setMessage("test022\n").call()
					.getId();

			commits[i] = commitId;
			i++;
		}
		git.commit().setAuthor(author).setCommitter(committer)
				.setMessage("test022\n").call().getId();

		Iterable<BlameEntry> blame = git.blame().setPath("test.txt").call();
		Iterator<BlameEntry> iterator = blame.iterator();

		ObjectId[] expectedCommits = new ObjectId[] { commits[2]
				commits[1] };
		Range[] expectedRanges = new Range[] { new Range(0
				new Range(3
		int[] expectedSuspectStarts = new int[] { 0
		for (i = 0; i < expectedCommits.length; i++) {
			BlameEntry blameEntry = iterator.next();
			assertTrue(blameEntry.guilty);
			assertEquals(expectedCommits[i]
			assertEquals(expectedRanges[i]
			assertEquals(expectedSuspectStarts[i]
		}
	}

	public void testMerge() throws Exception {

		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = "b 1\nb 2\nb 3\n";
		String c = "c 1\nc 2\nc 3\n";

		String authors[] = new String[] { "a"
		String versions[] = new String[] { a
		String parentCommitIdAuthors[] = new String[] { ""
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		int i = 0;
		ObjectInserter newInserter = repo.getObjectDatabase().newInserter();
		for (String version : versions) {
			ObjectId id = newInserter.insert(Constants.OBJ_BLOB
					version.getBytes(ENCODING));
			final Tree tree = new Tree(repo);
			tree.addEntry(new FileTreeEntry(tree
					"test".getBytes(ENCODING)
			ObjectId treeId = newInserter.insert(Constants.OBJ_TREE
					tree.format());

			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(authors[i]
			commit.setCommitter(new PersonIdent(authors[i]
			commit.setMessage("test022\n");
			commit.setTreeId(treeId);
			ArrayList<ObjectId> parentIds = new ArrayList<ObjectId>();
			for (String commitAuthor : parentCommitIdAuthors[i].split("
		Git git = new Git(repo);
		Iterable<BlameEntry> blame = git.blame().setPath("test")
				.setStartCommit(latestCommit).call();
		Iterator<BlameEntry> iterator = blame.iterator();

		ObjectId[] expectedCommitIds = new ObjectId[] { commitMap.get("ba")
				commitMap.get("a")
		Range[] expectedRanges = new Range[] { new Range(0
				new Range(3
		int[] expectedSuspectStarts = new int[] { 0
		for (i = 0; i < expectedCommitIds.length; i++) {
			BlameEntry blameEntry = iterator.next();
			assertTrue(blameEntry.guilty);
			assertEquals(expectedCommitIds[i]
					.copy());
			assertEquals(expectedRanges[i]
			assertEquals("entry " + i
					blameEntry.suspectStart);
		}
	}

	public void testMergeWithModifications() throws Exception {

		Repository repo = createWorkRepository();
		String a = "a 1\na 2\na 3\n";
		String b = "b 1\nb 2\nb 3\n";
		String c = "c 1\nc 2\nc 3\n";
		String x = "x 1\nx 2\nx 3\n";
		String y = "y 1\ny 2\ny 3\n";

		String authors[] = new String[] { "a"
		String versions[] = new String[] { a
		String parentCommitIdAuthors[] = new String[] { ""
		ArrayList<ObjectId> commitIds = new ArrayList<ObjectId>();
		HashMap<String
		int i = 0;
		ObjectInserter inserter = repo.getObjectDatabase().newInserter();
		for (String version : versions) {
			ObjectId id = inserter.insert(Constants.OBJ_BLOB
					version.getBytes(ENCODING));
			final Tree tree = new Tree(repo);
			tree.addEntry(new FileTreeEntry(tree
					"test".getBytes(ENCODING)
			ObjectId treeId = inserter
					.insert(Constants.OBJ_TREE

			final CommitBuilder commit = new CommitBuilder();
			commit.setAuthor(new PersonIdent(authors[i]
			commit.setCommitter(new PersonIdent(authors[i]
			commit.setMessage("test022\n");
			commit.setTreeId(treeId);
			ArrayList<ObjectId> parentIds = new ArrayList<ObjectId>();
			for (String commitAuthor : parentCommitIdAuthors[i].split("

		Git git = new Git(repo);
		Iterable<BlameEntry> blame = git.blame().setPath("test")
				.setStartCommit(latestCommit).call();
		Iterator<BlameEntry> iterator = blame.iterator();

		ObjectId[] expectedCommitIds = new ObjectId[] { commitMap.get("ba")
				commitMap.get("bxayc")
				commitMap.get("bxayc")
		Range[] expectedRanges = new Range[] { new Range(0
				new Range(3
				new Range(12
		int[] expectedSuspectStarts = new int[] { 0
		for (i = 0; i < expectedCommitIds.length; i++) {
			BlameEntry blameEntry = iterator.next();
			assertTrue(blameEntry.guilty);
			assertEquals(expectedCommitIds[i]
					.copy());
			assertEquals(expectedRanges[i]
			assertEquals("entry " + i
					blameEntry.suspectStart);
		}
	}

}
