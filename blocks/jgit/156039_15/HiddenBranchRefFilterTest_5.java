
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileSystemsTest {

	private static String VALUE = "sample";

	@Test
	public void basicSample() throws IOException {
		final Path result = Files.write(value.getPath("filename.txt")
		assertEquals(VALUE
	}
}
