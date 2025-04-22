
package org.eclipse.jgit.lfs.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lfs.errors.InvalidLongObjectIdException;
import org.eclipse.jgit.lfs.test.LongObjectIdTestUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LargeObjectPointerTest {

	@Test
	public void testEquals() {
		int size1 = 12345;
		LongObjectId id1 = LongObjectIdTestUtils.hash("test").toObjectId();
		LargeObjectPointer pointer1 = new LargeObjectPointer(id1
		assertTrue("pointer should equal itself"
		LargeObjectPointer pointer2 = new LargeObjectPointer(id1
		assertEquals("objects should be equals"

		LongObjectId id2 = LongObjectIdTestUtils.hash("other").toObjectId();
		pointer2 = new LargeObjectPointer(id2
		assertNotEquals("objects with different id should be not equal"
				pointer1

		pointer2 = new LargeObjectPointer(id1
		assertNotEquals("objects with size id should be not equal"
				pointer2);
	}
}
