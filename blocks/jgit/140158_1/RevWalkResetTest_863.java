
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;

import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Before;
import org.junit.Test;

public class RevWalkPathFilter6012Test extends RevWalkTestCase {
	private static final String pA = "pA"

	private RevCommit a

	private HashMap<RevCommit

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final RevBlob zF = blob("zF");
		final RevBlob zH = blob("zH");
		final RevBlob zI = blob("zI");
		final RevBlob zS = blob("zS");
		final RevBlob zY = blob("zY");

		a = commit(tree(file(pF
		b = commit(tree(file(pF
		c = commit(tree(file(pF
		d = commit(tree(file(pA
		parseBody(d);
		e = commit(d.getTree()
		f = commit(tree(file(pA
		parseBody(f);
		g = commit(tree(file(pE
		h = commit(f.getTree()
		i = commit(tree(file(pA

		byName = new HashMap<>();
		for (Field z : RevWalkPathFilter6012Test.class.getDeclaredFields()) {
			if (z.getType() == RevCommit.class)
				byName.put((RevCommit) z.get(this)
		}
	}

	protected void check(RevCommit... order) throws Exception {
		markStart(i);
		final StringBuilder act = new StringBuilder();
		for (RevCommit z : rw) {
			final String name = byName.get(z);
			assertNotNull(name);
			act.append(name);
			act.append(' ');
		}
		final StringBuilder exp = new StringBuilder();
		for (RevCommit z : order) {
			final String name = byName.get(z);
			assertNotNull(name);
			exp.append(name);
			exp.append(' ');
		}
		assertEquals(exp.toString()
	}

	protected void filter(String path) {
		rw.setTreeFilter(AndTreeFilter.create(PathFilterGroup
				.createFromStrings(Collections.singleton(path))
				TreeFilter.ANY_DIFF));
	}

	@Test
	public void test1() throws Exception {
		check(i
	}

	@Test
	public void test2() throws Exception {
		filter(pF);
	}

	@Test
	public void test3() throws Exception {
		rw.sort(RevSort.TOPO);
		filter(pF);
	}

	@Test
	public void test4() throws Exception {
		rw.sort(RevSort.COMMIT_TIME_DESC);
		filter(pF);
	}

	@Test
	public void test5() throws Exception {
		filter(pF);
	}

	@Test
	public void test6() throws Exception {
		filter(pF);
		check(i
	}

	@Test
	public void test7() throws Exception {
		rw.sort(RevSort.TOPO);
		filter(pF);
		check(i
	}
}
