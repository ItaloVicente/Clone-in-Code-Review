package org.eclipse.jgit.attributes.ident;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.util.IO;
import org.junit.Before;
import org.junit.Test;

public class IdentAttributeTest extends RepositoryTestCase {

	private Git git;

	private int workingTreeIndex;

	private int dirCachTreeIndex;

	private int revTreeIndex;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private static final FileMode D = FileMode.TREE;

	private static final String FAKE_BLOB_NAME = "3.14159265358979323846264338327950288419";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		workingTreeIndex = -1;
		dirCachTreeIndex = -1;
		revTreeIndex = -1;
	}

	@Test
	public void testCheckout_IdentAttrSet() throws Exception {
		writeTrashFile(".gitattributes"

		File fileWithIdent = writeTrashFile("AFile.withIdent"
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		git.checkout().setAllPaths(true).call();

				.build();

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdent
				"Id : $Id: c17c0ee73b62fa7ea04616bb0a2a50750544fea4 $");
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
				"Id : $Id: c17c0ee73b62fa7ea04616bb0a2a50750544fea4 $");
		assertModified(walk


		assertWorkingEntryContent(walk
	}

	@Test
	public void testCheckout_IdentAttrUnSet() throws Exception {
		writeTrashFile(".gitattributes"

		File fileWithIdent = writeTrashFile("AFile.withIdent"
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		git.checkout().setAllPaths(true).call();

				.build();

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdent
		assertWorkingEntryContent(walk
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
		assertModified(walk
	}

	@Test
	public void testCheckout_LocalIdentAttrChange() throws Exception {
		writeTrashFile(".gitattributes"

		File fileWithIdent = writeTrashFile("AFile.withIdent"
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		git.checkout().setAllPaths(true).call();

		writeTrashFile(".gitattributes"

				.build();

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdent
		assertWorkingEntryContent(walk
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
		assertModified(walk
	}

	@Test
	public void testCheckinOperation_IdentAttrSet() throws Exception {
		writeTrashFile(".gitattributes"

		String initialFileContent = "Id : $Id: " + FAKE_BLOB_NAME + " $";

		File fileWithIdent = writeTrashFile("AFile.withIdent"
				initialFileContent);
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

				.build();

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertModified(walk

				.call();

				.build();

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(fileWithIdent

		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
		assertModified(walk
	}

	@Test
	public void testCheckinOperation_LocalIdentAttrChange() throws Exception {
		writeTrashFile(".gitattributes"

		String initialFileContent = "Id : $Id: " + FAKE_BLOB_NAME + " $";

		writeTrashFile("AFile.withIdent"
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		writeTrashFile(".gitattributes"

		TreeWalk walk = new TWBuilder().withDirCach().build();

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk
		assertIndexEntryContent(walk

		RevCommit initialCommit = git.commit().setMessage("Initial commit")
				.call();
				.build();

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
		assertModified(walk
	}

	@Test
	public void testCheckinOperation_IdentAttrUnset() throws Exception {
		writeTrashFile(".gitattributes"

		String initialFileContent = "Id : $Id: " + FAKE_BLOB_NAME + " $";
		writeTrashFile("AFile.withIdent"
		writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		TreeWalk walk = new TWBuilder().withDirCach().build();

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk
		assertIndexEntryContent(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk

		RevCommit initialCommit = git.commit().setMessage("Initial commit")
				.call();

				.build();

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertIndexEntryContent(walk
		assertModified(walk
	}

	@Test
	public void testCheckinCheckout_IdentAttrSet() throws Exception {
		writeTrashFile(".gitattributes"

		String initialFileContent = "Id : $Id: " + FAKE_BLOB_NAME + " $";
		File fileWithIdent = writeTrashFile("AFile.withIdent"
				initialFileContent);
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		RevCommit initialCommit = git.commit().setMessage("Initial commit")
				.call();

				.call();

				.build();

		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(fileWithIdent
				"Id : $Id: c17c0ee73b62fa7ea04616bb0a2a50750544fea4 $");
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
				"Id : $Id: c17c0ee73b62fa7ea04616bb0a2a50750544fea4 $");
		assertModified(walk

	}

	@Test
	public void testCheckInCheckout_IdentAttrSetWithDelete() throws Exception {
		File attrFile = writeTrashFile(".gitattributes"

		String initialFileContent = "Id : $Id: " + FAKE_BLOB_NAME + " $";

		File fileWithIdent = writeTrashFile("AFile.withIdent"
				initialFileContent);
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		RevCommit initialCommit = git.commit().setMessage("Initial commit")
				.call();

		fileWithIdent.delete();

				.call();

				.build();

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertIndexEntryContent(walk
		assertFileContent(attrFile

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(fileWithIdent
				"Id : $Id: c17c0ee73b62fa7ea04616bb0a2a50750544fea4 $");

		assertIteration(walk

		assertIteration(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
				"Id : $Id: c17c0ee73b62fa7ea04616bb0a2a50750544fea4 $");
		assertModified(walk

	}

	@Test
	public void testMerge_IdentAttrSet() throws IOException
			NoFilepatternException
		writeTrashFile(".gitattributes"

		String fileFirstContent = "I have to believe in a world outside my own mind. I have to believe that my actions still have meaning
				+ "I have to believe that when my eyes are closed
				+ "We all need mirrors to remind ourselves who we are. I'm no different. $Id$";

		String fileFirstContentWithBlob = "I have to believe in a world outside my own mind. I have to believe that my actions still have meaning
				+ "I have to believe that when my eyes are closed
				+ "We all need mirrors to remind ourselves who we are. I'm no different. $Id: ab275d25f1582249d2d79089eaea8a740e540cb4 $";

		String movieFileName = "movie.script";

		File scriptFile = writeTrashFile(movieFileName

		git.add().addFilepattern(".gitattributes")
				.addFilepattern(movieFileName).call();

		RevCommit initialCommit = git.commit().setMessage("Initial commit")
				.call();


				.call();

				.build();

		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(scriptFile


		String fileSecondContent = "I have to believe in a world outside my own mind. I have to believe that my actions still have meaning
				+ "I have to believe that when my eyes are closed
				+ "$Id: ab275d25f1582249d2d79089eaea8a740e540cb4 $";

		String fileSecondContentNoBlob = "I have to believe in a world outside my own mind. I have to believe that my actions still have meaning
				+ "I have to believe that when my eyes are closed
				+ "$Id$";

		String fileSecondContentUpdateBlob = "I have to believe in a world outside my own mind. I have to believe that my actions still have meaning
				+ "I have to believe that when my eyes are closed
				+ "$Id: 63090a48733e1a29e457b25a42570e5c91eda49d $";

		writeTrashFile(movieFileName

		git.add().addFilepattern(movieFileName).call();

		RevCommit HEADBranchA = git.commit().setMessage("Branch A").call();

		git.checkout().setStartPoint(HEADBranchA).addPath(movieFileName)
				.call();

				.build();


		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(scriptFile

		git.checkout().setStartPoint(initialCommit).setCreateBranch(true)
				.setName("branchB").call();

		String fileThirdContent = "I have to believe that when my eyes are closed
				+ "We all need mirrors to remind ourselves who we are. I'm no different. $Id: ab275d25f1582249d2d79089eaea8a740e540cb4 $";

		String fileThirdContentNoBlob = "I have to believe that when my eyes are closed
				+ "We all need mirrors to remind ourselves who we are. I'm no different. $Id$";

		String fileThirdContentUpdatedBlob = "I have to believe that when my eyes are closed
				+ "We all need mirrors to remind ourselves who we are. I'm no different. $Id: 98cf252213350194cc5ff96d7cdcf30a1fa15f20 $";

		writeTrashFile(movieFileName

		git.add().addFilepattern(movieFileName).call();

		RevCommit HEADBranchB = git.commit().setMessage("branchB").call();

		git.checkout().setStartPoint(HEADBranchB).setCreateBranch(true)
				.addPath(movieFileName).call();

				.build();

		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(scriptFile

		MergeResult result = git.merge().include(HEADBranchA).call();
		assertTrue(result.getMergeStatus().isSuccessful());


				.build();

		assertIteration(walk

		String finalFileContent = "I have to believe that when my eyes are closed
				+ "$Id: cc69e1a11b8c9140c8a14baa4a397594365a5ff7 $\n";

		String finalFileContentNoBlob = "I have to believe that when my eyes are closed
				+ "$Id$\n";

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(scriptFile
	}

	@Test
	public void testCheckoutBranch() throws IOException
			NoFilepatternException
		writeTrashFile(".gitattributes"

		String initialFileContent = "Id : $Id: " + FAKE_BLOB_NAME + " $";

		File fileWithIdent = writeTrashFile("AFile.withIdent"
				initialFileContent);
		File fileWithIdentInSubTree = writeTrashFile(
				"folder1/AFile2.withIdent"

				.call();

		RevCommit initialCommit = git.commit().setMessage("Initial commit")
				.call();

		String newFileContent = initialFileContent + "extrat content";
		writeTrashFile("AFile.withIdent"

		writeTrashFile("folder1/AFile2.withIdent"

		git.checkout().setCreateBranch(true).setName("newBranch")
				.setStartPoint(initialCommit).call();

				.build();

		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertRevTreeEntryContent(walk
		assertFileContent(fileWithIdent
		assertModified(walk

		assertIteration(walk

		assertIteration(walk
		assertWorkingEntryContent(walk
		assertIndexEntryContent(walk
		assertFileContent(fileWithIdentInSubTree
		assertModified(walk

	}

	private void assertModified(TreeWalk walk
			throws IOException {
		WorkingTreeIterator workingTreeIterator = walk.getTree(
				workingTreeIndex
		assertNotNull(workingTreeIterator);
		DirCacheIterator dirCacheIterator = walk.getTree(dirCachTreeIndex
				DirCacheIterator.class);
		assertNotNull(dirCacheIterator);
		boolean actualModified = workingTreeIterator.isModified(
				dirCacheIterator.getDirCacheEntry()
				false
		assertTrue(modified == actualModified);

	}

	private void assertIteration(TreeWalk walk
			throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		if (D.equals(type))
			walk.enterSubtree();
	}

	private void assertFileContent(File file
			throws FileNotFoundException
		assertThat(new String(IO.readFully(file))
	}

	private void assertWorkingEntryContent(TreeWalk walk
			throws IOException {
		WorkingTreeIterator itr = walk.getTree(workingTreeIndex
				WorkingTreeIterator.class);
		assertNotNull("has WorkingTreeIterator"

		InputStream openEntryStream = itr.openEntryStream();
		assertThat(toString(openEntryStream)
	}

	static String toString(java.io.InputStream is) throws IOException {
		java.util.Scanner scanner = new java.util.Scanner(is
		scanner.useDelimiter("\\A");
		try {
			String result = scanner.hasNext() ? scanner.next() : "";
			return result;
		} finally {
			scanner.close();
			is.close();
		}
	}

	private void assertIndexEntryContent(TreeWalk walk
			throws IOException {
		DirCacheIterator itr = walk.getTree(dirCachTreeIndex
				DirCacheIterator.class);
		assertNotNull("has WorkingTreeIterator"

		String actual = new String(db.open(
				itr.getDirCacheEntry().getObjectId()
				.getCachedBytes()
		assertThat(actual
	}

	private void assertRevTreeEntryContent(TreeWalk walk
			throws IOException {
		CanonicalTreeParser itr = walk.getTree(revTreeIndex
				CanonicalTreeParser.class);
		assertNotNull("has WorkingTreeIterator"

		String actual = new String(db.open(itr.getEntryObjectId()
				Constants.OBJ_BLOB).getCachedBytes()
		assertThat(actual

	}

	private class TWBuilder {

		private boolean withWorkingTreeIte;

		private boolean withDirCachIte;

		private ObjectId withRetree;

		public TWBuilder withWorkingTree() {
			withWorkingTreeIte = true;
			return this;
		}

		public TWBuilder withDirCach() {
			withDirCachIte = true;
			return this;
		}

		public TWBuilder withRevTree(ObjectId tree) {
			this.withRetree = tree;
			return this;
		}


		public TreeWalk build() throws MissingObjectException
				IncorrectObjectTypeException
				IOException {
			TreeWalk walk = new TreeWalk(db);
			if (withRetree != null)
				revTreeIndex = walk.addTree(withRetree);
			if (withWorkingTreeIte)
				workingTreeIndex = walk.addTree(new FileTreeIterator(db));
			if (withDirCachIte)
				dirCachTreeIndex = walk.addTree(new DirCacheIterator(db
						.readDirCache()));
			return walk;
		}

	}

}
