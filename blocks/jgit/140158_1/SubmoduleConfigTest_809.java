package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public class SquashCommitMsgTest extends RepositoryTestCase {
	private static final String squashMsg = "squashed commit";

	@Test
	public void testReadWriteMergeMsg() throws IOException {
		assertEquals(db.readSquashCommitMsg()
		assertFalse(new File(db.getDirectory()
		db.writeSquashCommitMsg(squashMsg);
		assertEquals(squashMsg
		assertEquals(read(new File(db.getDirectory()
				squashMsg);
		db.writeSquashCommitMsg(null);
		assertEquals(db.readSquashCommitMsg()
		assertFalse(new File(db.getDirectory()
		try (FileOutputStream fos = new FileOutputStream(
				new File(db.getDirectory()
			fos.write(squashMsg.getBytes(UTF_8));
		}
		assertEquals(db.readSquashCommitMsg()
	}
}
