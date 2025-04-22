package org.eclipse.jgit.niofs.internal;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Random;

import org.eclipse.jgit.niofs.JGitFileSystemBuilder;
import org.junit.Test;

public class JGitFileSystemBuilderTest extends BaseTest {

	@Test
	public void testSimpleBuilderSample() throws IOException {
		final FileSystem fs = JGitFileSystemBuilder.newFileSystem("myrepo" + new Random().nextInt());

		Path foo = fs.getPath("/foo");
		Files.createDirectory(foo);


		Files.write(hello

		assertEquals("hello world"
	}
}
