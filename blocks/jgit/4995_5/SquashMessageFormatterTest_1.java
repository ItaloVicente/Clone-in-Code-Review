package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
		FileOutputStream fos = new FileOutputStream(new File(db.getDirectory()
				Constants.SQUASH_MSG));
		try {
			fos.write(squashMsg.getBytes(Constants.CHARACTER_ENCODING));
		} finally {
			fos.close();
		}
		assertEquals(db.readSquashCommitMsg()
	}
}
