package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public class MergeHeadMsgTest extends RepositoryTestCase {
	private static final String mergeMsg = "merge a and b";

	private static final String sampleId = "1c6db447abdbb291b25f07be38ea0b1bf94947c5";

	@Test
	public void testReadWriteMergeHeads() throws IOException {
		assertEquals(db.readMergeHeads()
		db.writeMergeHeads(Arrays.asList(ObjectId.zeroId()
				ObjectId.fromString(sampleId)));
		assertEquals(read(new File(db.getDirectory()
		assertEquals(db.readMergeHeads().size()
		assertEquals(db.readMergeHeads().get(0)
		assertEquals(db.readMergeHeads().get(1)

		try (FileOutputStream fos = new FileOutputStream(
				new File(db.getDirectory()
			fos.write(
					"0000000000000000000000000000000000000000\n1c6db447abdbb291b25f07be38ea0b1bf94947c5\n"
							.getBytes(UTF_8));
		}
		assertEquals(db.readMergeHeads().size()
		assertEquals(db.readMergeHeads().get(0)
		assertEquals(db.readMergeHeads().get(1)
		db.writeMergeHeads(Collections.<ObjectId> emptyList());
		assertEquals(read(new File(db.getDirectory()
		assertEquals(db.readMergeHeads()
		try (FileOutputStream fos = new FileOutputStream(
				new File(db.getDirectory()
			fos.write(sampleId.getBytes(UTF_8));
		}
		assertEquals(db.readMergeHeads().size()
		assertEquals(db.readMergeHeads().get(0)
	}

	@Test
	public void testReadWriteMergeMsg() throws IOException {
		assertEquals(db.readMergeCommitMsg()
		assertFalse(new File(db.getDirectory()
		db.writeMergeCommitMsg(mergeMsg);
		assertEquals(db.readMergeCommitMsg()
		assertEquals(read(new File(db.getDirectory()
		db.writeMergeCommitMsg(null);
		assertEquals(db.readMergeCommitMsg()
		assertFalse(new File(db.getDirectory()
		try (FileOutputStream fos = new FileOutputStream(
				new File(db.getDirectory()
			fos.write(mergeMsg.getBytes(UTF_8));
		}
		assertEquals(db.readMergeCommitMsg()
	}

}
