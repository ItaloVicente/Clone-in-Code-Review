package org.eclipse.jgit.storage.file;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileBasedConfigTest {

	private static final String USER = "user";

	private static final String NAME = "name";

	private static final String ALICE = "Alice";

	private static final String BOB = "Bob";

	private static final String CONTENT1 = "[" + USER + "]\n\t" + NAME + " = "
			+ ALICE + "\n";

	private static final String CONTENT2 = "[" + USER + "]\n\t" + NAME + " = "
			+ BOB + "\n";

	private File trash;

	private List<File> files = new ArrayList<File>();

	@Before
	public void setUp() throws IOException {
		trash = File.createTempFile("jgit"
		trash.delete();
		trash.mkdir();
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.delete(trash
	}

	@Test
	public void testSystemEncoding() throws IOException
		final File file = createFile(CONTENT1.getBytes());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();
		assertArrayEquals(CONTENT2.getBytes()
	}

	@Test
	public void testUTF8withoutBOM() throws IOException
		final File file = createFile(CONTENT1.getBytes("UTF-8"));
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();
		assertArrayEquals(CONTENT2.getBytes()
	}

	@Test
	public void testUTF8withBOM() throws IOException
		final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
		bos1.write(0xEF);
		bos1.write(0xBB);
		bos1.write(0xBF);
		bos1.write(CONTENT1.getBytes("UTF-8"));

		final File file = createFile(bos1.toByteArray());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();

		final ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		bos2.write(0xEF);
		bos2.write(0xBB);
		bos2.write(0xBF);
		bos2.write(CONTENT2.getBytes("UTF-8"));
		assertArrayEquals(bos2.toByteArray()
	}

	private File createFile(byte[] content) throws IOException {
		trash.mkdirs();
		File f = File.createTempFile(getClass().getName()
		FileOutputStream os = new FileOutputStream(f
		try {
			os.write(content);
		} finally {
			os.close();
		}
		files.add(f);
		return f;
	}
}
