package org.eclipse.jgit.diff;

import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class RawTextLoadTest extends RepositoryTestCase {
	private static byte[] generate(int size
		byte[] data = new byte[size];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ((i % 72 == 0) ? '\n' : (i%10) + '0');
		}
		if (nullAt >= 0) {
			data[nullAt] = '\0';
		}
		return data;
	}

	private RawText textFor(byte[] data
		FileRepository repo = createBareRepository();
		ObjectId id;
		try (ObjectInserter ins = repo.getObjectDatabase().newInserter()) {
			id = ins.insert(Constants.OBJ_BLOB
		}
		ObjectLoader ldr = repo.open(id);
		return RawText.load(ldr
	}

	@Test
	public void testSmallOK() throws Exception {
		byte[] data = generate(1000
		RawText result = textFor(data
		Assert.assertArrayEquals(result.content
	}

	@Test(expected = BinaryBlobException.class)
	public void testSmallNull() throws Exception {
		byte[] data = generate(1000
		textFor(data
	}

	@Test
	public void testBigOK() throws Exception {
		byte[] data = generate(10000
		RawText result = textFor(data
		Assert.assertArrayEquals(result.content
	}

	@Test(expected = BinaryBlobException.class)
	public void testBigWithNullAtStart() throws Exception {
		byte[] data = generate(10000
		textFor(data
	}

	@Test(expected = BinaryBlobException.class)
	public void testBinaryThreshold() throws Exception {
		byte[] data = generate(2 << 20
		textFor(data
	}
}
