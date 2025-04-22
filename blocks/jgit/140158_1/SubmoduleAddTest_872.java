package org.eclipse.jgit.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.util.FileUtils.pathToString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

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

	private static final String EMAIL = "email";

	private static final String ALICE = "Alice";

	private static final String BOB = "Bob";

	private static final String ALICE_EMAIL = "alice@home";

	private static final String CONTENT1 = "[" + USER + "]\n\t" + NAME + " = "
			+ ALICE + "\n";

	private static final String CONTENT2 = "[" + USER + "]\n\t" + NAME + " = "
			+ BOB + "\n";

	private static final String CONTENT3 = "[" + USER + "]\n\t" + NAME + " = "
			+ ALICE + "\n" + "[" + USER + "]\n\t" + EMAIL + " = " + ALICE_EMAIL;

	private File trash;

	@Before
	public void setUp() throws Exception {
		trash = File.createTempFile("tmp_"
		trash.delete();
		assertTrue("mkdir " + trash
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.delete(trash
	}

	@Test
	public void testSystemEncoding() throws IOException
		final File file = createFile(CONTENT1.getBytes(UTF_8));
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();
		assertArrayEquals(CONTENT2.getBytes(UTF_8)
	}

	@Test
	public void testUTF8withoutBOM() throws IOException
		final File file = createFile(CONTENT1.getBytes(UTF_8));
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();
		assertArrayEquals(CONTENT2.getBytes(UTF_8)
	}

	@Test
	public void testUTF8withBOM() throws IOException
		final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
		bos1.write(0xEF);
		bos1.write(0xBB);
		bos1.write(0xBF);
		bos1.write(CONTENT1.getBytes(UTF_8));

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
		bos2.write(CONTENT2.getBytes(UTF_8));
		assertArrayEquals(bos2.toByteArray()
	}

	@Test
	public void testLeadingWhitespaces() throws IOException
		final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
		bos1.write(" \n\t".getBytes(UTF_8));
		bos1.write(CONTENT1.getBytes(UTF_8));

		final File file = createFile(bos1.toByteArray());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();

		final ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		bos2.write(" \n\t".getBytes(UTF_8));
		bos2.write(CONTENT2.getBytes(UTF_8));
		assertArrayEquals(bos2.toByteArray()
	}

	@Test
	public void testIncludeAbsolute()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes(UTF_8));
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes(UTF_8));
		bos.write(pathToString(includedFile).getBytes(UTF_8));

		final File file = createFile(bos.toByteArray());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeRelativeDot()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes(UTF_8)
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes(UTF_8));
		bos.write(("./" + includedFile.getName()).getBytes(UTF_8));

		final File file = createFile(bos.toByteArray()
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeRelativeDotDot()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes(UTF_8)
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes(UTF_8));
		bos.write(("../" + includedFile.getParentFile().getName() + "/"
				+ includedFile.getName()).getBytes(UTF_8));

		final File file = createFile(bos.toByteArray()
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeRelativeDotDotNotFound()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes(UTF_8));
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes(UTF_8));
		bos.write(("../" + includedFile.getName()).getBytes(UTF_8));

		final File file = createFile(bos.toByteArray());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(null
	}

	@Test
	public void testIncludeWithTilde()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes(UTF_8)
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes(UTF_8));
		bos.write(("~/" + includedFile.getName()).getBytes(UTF_8));

		final File file = createFile(bos.toByteArray()
		final FS fs = FS.DETECTED.newInstance();
		fs.setUserHome(includedFile.getParentFile());

		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeDontInlineIncludedLinesOnSave()
			throws IOException
		final File includedFile = createFile(CONTENT3.getBytes(UTF_8)

		final File file = createFile(new byte[0]
		FileBasedConfig config = new FileBasedConfig(file
		config.setString("include"
				("../" + includedFile.getParentFile().getName() + "/"
						+ includedFile.getName()));

		assertEquals(null
		assertEquals(null
		config.save();

		assertEquals(null
		assertEquals(null

		final String expectedText = config.toText();
		assertEquals(2
				new StringTokenizer(expectedText

		config = new FileBasedConfig(file
		config.load();

		String actualText = config.toText();
		assertEquals(expectedText
		assertEquals(ALICE
		assertEquals(ALICE_EMAIL

		config.save();

		actualText = config.toText();
		assertEquals(expectedText
		assertEquals(ALICE
		assertEquals(ALICE_EMAIL
	}

	private File createFile(byte[] content) throws IOException {
		return createFile(content
	}

	private File createFile(byte[] content
		File dir = subdir != null ? new File(trash
		dir.mkdirs();

		File f = File.createTempFile(getClass().getName()
		try (FileOutputStream os = new FileOutputStream(f
			os.write(content);
		}
		return f;
	}
}
