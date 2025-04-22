package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileSnapshotTest {

	List<File> files = new ArrayList<File>();

	@Before
	public void tearDown() throws Exception {
		for (File f : files)
			f.delete();
	}

	public void waitNextSec(File f) {
		long initialLastModified = f.lastModified();
		do {
			f.setLastModified(System.currentTimeMillis());
		} while (f.lastModified() == initialLastModified);
	}

	@Test
	public void testActuallyIsModifiedTrivial() throws Exception {
		File f1 = createFile("simple");
		waitNextSec(f1);
		FileSnapshot save = FileSnapshot.save(f1);
		append(f1
		waitNextSec(f1);
		assertTrue(save.isModified(f1));
	}

	@Test
	public void testOldFile() throws Exception {
		File f1 = createFile("oldfile");
		waitNextSec(f1);
		FileSnapshot save = FileSnapshot.save(f1);
		Thread.sleep(3500);
		assertFalse(save.isModified(f1));
	}

	@Test
	public void testNewFileWithWait() throws Exception {
		File f1 = createFile("newfile");
		waitNextSec(f1);
		FileSnapshot save = FileSnapshot.save(f1);
		Thread.sleep(1500);
		assertTrue(save.isModified(f1));
	}

	@Test
	public void testNewFileNoWait() throws Exception {
		File f1 = createFile("newfile");
		waitNextSec(f1);
		FileSnapshot save = FileSnapshot.save(f1);
		Thread.sleep(1500);
		assertTrue(save.isModified(f1));
	}

	private File createFile(String string) throws IOException {
		File f = File.createTempFile(string
		files.add(f);
		return f;
	}

	private void append(File f
		FileOutputStream os = new FileOutputStream(f
		os.write(b);
		os.close();
	}

}
