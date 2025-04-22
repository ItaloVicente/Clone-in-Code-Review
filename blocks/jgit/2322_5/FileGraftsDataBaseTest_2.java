package org.eclipse.jgit.storage.file;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class FileGraftsDataBaseTest extends LocalDiskRepositoryTestCase {

	ObjectId id1 = ObjectId
			.fromString("3423254356356456456425645245614256410001");

	ObjectId id2 = ObjectId
			.fromString("342a254356356456456425645245614256410002");

	ObjectId id3 = ObjectId
			.fromString("3423c54356356456456425645245614256410003");

	ObjectId id4 = ObjectId
			.fromString("34232f4356356456456425645245614256410004");

	@Test
	public void testEmptyGraftsDb() throws IOException {
		FileRepository fill = fill("");
		assertEquals(0
	}

	@Test
	public void testOneLineNoParentGraftsDb() throws IOException {
		FileRepository fill = fill(id1.name() + "\n");
		assertEquals(1
		assertEquals(0
		assertNull(fill.getGrafts().get(id4));
	}

	@Test
	public void testOneLineOneParentGraftsDb() throws IOException {
		FileRepository fill = fill(id1.name() + " " + id2.name() + "\n");
		assertEquals(1
		assertEquals(1
		assertEquals(id2
		assertNull(fill.getGrafts().get(id4));
	}

	@Test
	public void testOneLineTwoParentGraftsDb() throws IOException {
		FileRepository fill = fill(id1.name() + " " + id2.name() + " "
				+ id3.name() + "\n");
		assertEquals(1
		assertEquals(2
		assertEquals(id2
		assertEquals(id3
		assertNull(fill.getGrafts().get(id4));
	}

	@Test
	public void testMultiLineGraftsDb() throws IOException {
		FileRepository fill = fill(id1.name() + " " + id2.name() + "\n"
				+ id3.name() + " " + id2.name() + " " + id4.name() + "\n");
		assertEquals(2
		assertEquals(1
		assertEquals(id2
		assertEquals(2
		assertEquals(id2
		assertEquals(id4
	}

	private FileRepository fill(String string) throws IOException {
		FileRepository repo = super.createWorkRepository();
		File file = repo.getGraftsFile();
		file.getParentFile().mkdir();
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(string);
		fileWriter.close();
		return repo;
	}

}
