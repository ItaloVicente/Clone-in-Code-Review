package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileSnapshotTest {

	private List<File> files = new ArrayList<>();

	private File trash;

	@Before
	public void setUp() throws Exception {
		trash = File.createTempFile("tmp_"
		trash.delete();
		assertTrue("mkdir " + trash
	}

	@Before
	@After
	public void tearDown() throws Exception {
		FileUtils.delete(trash
	}

	private static void waitNextSec(File f) {
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
		trash.mkdirs();
		File f = File.createTempFile(string
		files.add(f);
		return f;
	}

	private static void append(File f
		try (FileOutputStream os = new FileOutputStream(f
			os.write(b);
		}
	}

}
