
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jgit.internal.storage.file.FileReftableStack.Segment;
import org.eclipse.jgit.internal.storage.reftable.MergedReftable;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReftableStackTest {

	private static Ref newRef(String name
		return new ObjectIdRef.PeeledNonTag(PACKED
	}

	private File reftableDir;

	@Before
	public void setup() throws Exception {
		reftableDir = FileUtils.createTempDir("rtstack"
	}

	@After
	public void tearDown() throws Exception {
		if (reftableDir != null) {
			FileUtils.delete(reftableDir
		}
	}

	void writeBranches(FileReftableStack stack
		for (int i = 0; i < N; i++) {
			while (true) {
				final long next = stack.getMergedReftable().maxUpdateIndex()
				+ 1;

				System.err.println(next + template);
				String name = String.format(template
				Ref r = newRef(name
				boolean ok = stack.addReftable(rw -> {
					rw.setMinUpdateIndex(next).setMaxUpdateIndex(next).begin()
						.writeRef(r);
				});
				if (ok) { break; }
			}
		}
	}

	public void testCompaction(int N) throws Exception {
		try (FileReftableStack stack = new FileReftableStack(
				new File(reftableDir
				() -> new Config())) {
			writeBranches(stack
			MergedReftable table = stack.getMergedReftable();
			for (int i = 1; i < N; i++) {
				String name = String.format("refs/heads/branch%d"
						Integer.valueOf(i));
				RefCursor c = table.seekRef(name);
				assertTrue(c.next());
				assertEquals(ObjectId.zeroId()
			}

			List<String> files = Arrays.asList(reftableDir.listFiles()).stream()
					.map(File::getName).collect(Collectors.toList());
			Collections.sort(files);

			assertTrue(files.size() < 20);

			FileReftableStack.CompactionStats stats = stack.getStats();
			assertEquals(0
			assertTrue(stats.attempted < N);
			assertTrue(stats.refCount < FileReftableStack.log(N) * N);
		}
	}

	@Test
	public void testCompaction9() throws Exception {
		testCompaction(9);
	}

	@Test
	public void testCompaction1024() throws Exception {
		testCompaction(1024);
	}

	@Test
	public void testRacyReload() throws Exception {
		try (FileReftableStack stack1 = new FileReftableStack(
			new File(reftableDir
			() -> new Config());
			FileReftableStack stack2 = new FileReftableStack(
				new File(reftableDir
				() -> new Config());
		) {
			FileReftableStack stacks[] = {stack1
			Exception es[] = {null
			Thread ts[] = {null
			for (int i = 0; i < 2; i++) {
				final int j = i;
				ts[i] = new Thread(() -> {
					try {
						writeBranches(stacks[j]
							j * 50
					} catch (Exception e) {
						es[j] = e;
					}
				});
				ts[i].run();
			}
			ts[0].join();
			ts[1].join();

			MergedReftable table = stacks[0].getMergedReftable();
			for (int i = 1; i < 100; i++) {
				String name = String.format("stack/branch%d"
					Integer.valueOf(i));
				RefCursor c = table.seekRef(name);
				assertTrue(c.next());
				assertEquals(ObjectId.zeroId()
			}
		}
	}

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void missingReftable() throws Exception {
		try (FileReftableStack stack = new FileReftableStack(
			new File(reftableDir
			() -> new Config())) {

			for (int i = 0; i < 10; i++) {
				final long next = stack.getMergedReftable().maxUpdateIndex()
					+ 1;
				String name = String.format("branch%d"
				Ref r = newRef(name
				boolean ok = stack.addReftable(rw -> {
					rw.setMinUpdateIndex(next).setMaxUpdateIndex(next).begin()
						.writeRef(r);
				});
				assertTrue(ok);

				List<File> files = Arrays.asList(reftableDir.listFiles());
				if (files.size() > 1) {
					files.get(0).delete();
					break;
				}
			}
		}
		thrown.expect(FileNotFoundException.class);
		try (FileReftableStack stack = new FileReftableStack(
			new File(reftableDir
			() -> new Config())) {
		}
	}

	@Test
	public void testSegments() {
		long in [] = { 1024
		List<Segment> got = FileReftableStack.segmentSizes(in);
		Segment want[] = {
			new Segment(0
			new Segment(3
			new Segment(5
			new Segment(6
		};
		assertEquals(got.size()
		for (int i = 0; i < want.length;i++) {
			assertTrue(want[i].equals( got.get(i)));
		}
	}

	@Test
	public void testLog2() throws Exception {
		assertEquals(10
		assertEquals(10
		assertEquals(10
	}
}
