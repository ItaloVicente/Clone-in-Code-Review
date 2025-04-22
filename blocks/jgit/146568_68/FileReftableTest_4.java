package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jgit.internal.storage.reftable.MergedReftable;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileReftableStackTest {

	private static Ref newRef(String name
		return new ObjectIdRef.PeeledNonTag(PACKED
	}

	File tmp;

	@Before
	public void setup() throws Exception {
		tmp = FileUtils.createTempDir("rtstack"
	}

	@After
	public void tearDown() throws Exception {
		if (tmp != null) {
			FileUtils.delete(tmp
		}
	}

	@Test
	public void testCompaction() throws Exception {
		FileReftableStack stack = new FileReftableStack(new File(tmp
				tmp

		int N = 1024;
		for (int i = 1; i < N; i++) {
			final int j = i;
			String name = String.format("refs/heads/branch%d"
			Ref r = newRef(name
			boolean ok = stack.addReftable(rw -> {
				rw.setMinUpdateIndex(j).setMaxUpdateIndex(j).begin()
						.writeRef(r);
			});
			assertTrue(ok);
		}

		MergedReftable table = stack.getMergedReftable();
		for (int i = 1; i < N; i++) {
			String name = String.format("refs/heads/branch%d"
			RefCursor c = table.seekRef(name);
			assertTrue(c.next());
			assertEquals(ObjectId.zeroId()
		}

		List<String> files = Arrays.asList(tmp.listFiles()).stream()
				.map(x -> x.getName()).collect(Collectors.toList());
		Collections.sort(files);

		assertTrue(files.size() < 20);

		FileReftableStack.CompactionStats stats = stack.getStats();
		assertTrue(stats.count < N);
	}
}
