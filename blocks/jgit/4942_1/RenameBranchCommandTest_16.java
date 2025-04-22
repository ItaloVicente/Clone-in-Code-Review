package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Ignore;
import org.junit.Test;

public class HugeFileTest extends RepositoryTestCase {

	private long t = System.currentTimeMillis();

	private long lastt = t;

	private void measure(String name) {
		long c = System.currentTimeMillis();
		System.out.println(name + "
		lastt = c;
	}

	@Ignore("Test takes way too long (~10 minutes) to be part of the standard suite")
	@Test
	public void testAddHugeFile() throws Exception {
		measure("Commencing test");
		File file = new File(db.getWorkTree()
		RandomAccessFile rf = new RandomAccessFile(file
		rf.setLength(4429185024L);
		rf.close();
		measure("Created file");
		Git git = new Git(db);

		git.add().addFilepattern("a.txt").call();
		measure("Added file");
		assertEquals(
				"[a.txt
				indexState(LENGTH | CONTENT_ID));

		Status status = git.status().call();
		measure("Status after add");
		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0

		rf = new RandomAccessFile(file
		rf.write(0);
		rf.close();

		status = git.status().call();
		measure("Status after non-modifying update");

		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0

		rf = new RandomAccessFile(file
		rf.write('a');
		rf.close();

		status = git.status().call();
		measure("Status after modifying update");

		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0

		rf = new RandomAccessFile(file
		rf.setLength(134217728L);
		rf.write(0);
		rf.close();

		status = git.status().call();
		measure("Status after truncating update");

		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0

		rf = new RandomAccessFile(file
		rf.write('a');
		rf.close();

		status = git.status().call();
		measure("Status after modifying and truncating update");

		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0

		rf = new RandomAccessFile(file
		rf.setLength(3429185024L);
		rf.write(0);
		rf.close();

		git.add().addFilepattern("a.txt").call();
		measure("Added truncated file");
		assertEquals(
				"[a.txt
				indexState(LENGTH | CONTENT_ID));

		status = git.status().call();
		measure("Status after status on truncated file");

		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0

		rf = new RandomAccessFile(file
		rf.write('a');
		rf.close();

		status = git.status().call();
		measure("Status after modifying and truncating update");

		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0

		git.commit().setMessage("make a commit").call();
		measure("After commit");
		status = git.status().call();
		measure("After status after commit");

		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertCollectionEquals(Arrays.asList("a.txt")
		assertEquals(0
		assertEquals(0

		git.reset().setMode(ResetType.HARD).call();
		measure("After reset --hard");
		assertEquals(
				"[a.txt
				indexState(LENGTH | CONTENT_ID));

		status = git.status().call();
		measure("Status after hard reset");

		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	private void assertCollectionEquals(Collection<?> asList
			Collection<?> added) {
		assertEquals(asList.toString()
	}

}
