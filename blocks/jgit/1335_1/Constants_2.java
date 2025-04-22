package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.pgm.CatFile.CatType;
import org.eclipse.jgit.storage.file.FileRepository;

public class CatFileTest extends LocalDiskRepositoryTestCase {

	public void testShouldReadContentsOfCommitsByObjectId() throws Exception {
		FileRepository repo = repo();

		CatFile cat = new CatFile();
		cat.init(repo
		cat.objectName = "279dae73ebf5f028bcac84afb3b40794f4ae60d4";
		cat.type = CatType.COMMIT;
		StringWriter out = new StringWriter();
		cat.out = new PrintWriter(out);
		cat.run();

		assertEquals(
						"Initial commit"
				out.getBuffer().toString());
	}

	public void testShouldReadContentsOfBlobsByObjectId() throws Exception {
		FileRepository repo = repo();

		CatFile cat = new CatFile();
		cat.init(repo
		cat.objectName = "e916b49e039faf8ee2f3e61c4678d88fa4c09e40";
		cat.type = CatType.BLOB;
		StringWriter out = new StringWriter();
		cat.out = new PrintWriter(out);
		cat.run();

		assertEquals("This project does foo!"
	}

	public void testShouldReadContentsOfTreesByObjectId() throws Exception {
		FileRepository repo = repo();

		CatFile cat = new CatFile();
		cat.init(repo
		cat.objectName = "c7cb03cd4c57db844ca695a23123ce4990a648d1";
		cat.type = CatType.TREE;
		StringWriter out = new StringWriter();
		cat.out = new PrintWriter(out);
		cat.run();

		assertEquals("100644 README"
	}

	public void testShouldReadContentsOfTagsByObjectId() throws Exception {
		FileRepository repo = repo();

		CatFile cat = new CatFile();
		cat.init(repo
		cat.objectName = "v0.1b";
		cat.type = CatType.TAG;
		StringWriter out = new StringWriter();
		cat.out = new PrintWriter(out);
		cat.run();

				"Tagging for release 0.1b"
				out.getBuffer().toString());
	}

	public void testShouldThrowErrorOnInvalidObjectId() throws Exception {
		FileRepository repo = repo();

		CatFile cat = new CatFile();
		cat.init(repo
		cat.objectName = "does-not-exist";
		cat.type = CatType.BLOB;
		StringWriter out = new StringWriter();
		cat.out = new PrintWriter(out);
		try {
			cat.run();
		} catch (Die e) {
			assertEquals("does-not-exist is not a valid object name"
		}
	}

	private FileRepository repo() throws Exception {
		FileRepository repo = createWorkRepository();
		write(new File(repo.getWorkTree()
				"This project does foo!");
		Git git = new Git(repo);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();

		ObjectId head = repo.resolve("head");
		final ObjectLoader ldr = repo.open(head);

		org.eclipse.jgit.lib.Tag tag = new org.eclipse.jgit.lib.Tag(repo);
		tag.setObjId(head);
		tag.setType(Constants.typeString(ldr.getType()));
		tag.setMessage("Tagging for release 0.1b");
		tag.setTag("v0.1b");
		tag.tag();

		return repo;
	}

}
