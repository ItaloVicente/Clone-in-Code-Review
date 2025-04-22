package org.eclipse.jgit.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class MergeHeadMsgTest extends RepositoryTestCase {
	private static final String mergeMsg = "merge a and b";

	private static final String sampleId = "1c6db447abdbb291b25f07be38ea0b1bf94947c5";

	public void testReadWriteMergeHeads() throws IOException {
		assertEquals(db.readMergeHeads()
		db.writeMergeHeads(Arrays.asList(ObjectId.zeroId()
				ObjectId.fromString(sampleId)));
		assertEquals(db.readMergeHeads().size()
		assertEquals(db.readMergeHeads().get(0)
		assertEquals(db.readMergeHeads().get(1)
		db.writeMergeHeads(Collections.EMPTY_LIST);
		assertEquals(db.readMergeHeads()
		FileOutputStream fos = new FileOutputStream(new File(db.getDirectory()
				Constants.MERGE_HEAD));
		try {
			fos.write(sampleId.getBytes(Constants.CHARACTER_ENCODING));
		} finally {
			fos.close();
		}
		assertEquals(db.readMergeHeads().size()
		assertEquals(db.readMergeHeads().get(0)
	}

	public void testReadWriteMergeMsg() throws IOException {
		assertEquals(db.readMergeCommitMsg()
		db.writeMergeCommitMsg(mergeMsg);
		assertEquals(db.readMergeCommitMsg()
		db.writeMergeCommitMsg(null);
		assertEquals(db.readMergeCommitMsg()
		FileOutputStream fos = new FileOutputStream(new File(db.getDirectory()
				Constants.MERGE_MSG));
		try {
			fos.write(mergeMsg.getBytes(Constants.CHARACTER_ENCODING));
		} finally {
			fos.close();
		}
		assertEquals(db.readMergeCommitMsg()
	}

}
