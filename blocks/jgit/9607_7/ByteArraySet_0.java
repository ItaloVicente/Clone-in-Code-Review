package org.eclipse.jgit.treewalk.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class PathFilterGroupTest2 {

	private DirCache dc;

	private String[] paths = new String[100000];

	private TreeFilter pf;

	private String data;

	private long tInit;

	public void setup(int pathsize
		long t1 = System.nanoTime();
		String[] seed = { "abc"
				"vwx"
		dc = DirCache.newInCore();
		DirCacheEditor ed = dc.editor();
		int pi = 0;
		B: for (String a : seed) {
			for (String b : seed) {
				for (String c : seed) {
					for (String d : seed) {
						for (String e : seed) {
							if (pi >= pathsize)
								break B;
							String p1 = a + "/" + b + "/" + c + "/" + d + "/"
									+ e;
							paths[pi] = p1;
							ed.add(new DirCacheEditor.PathEdit(p1) {

								@Override
								public void apply(DirCacheEntry ent) {
									ent.setFileMode(FileMode.REGULAR_FILE);
								}
							});
							++pi;
						}
					}
				}
			}
		}
		ed.finish();
		long t2 = System.nanoTime();
		if (filters != null)
			pf = PathFilterGroup.createFromStrings(filters);
		else {
			String[] filterPaths = new String[filtersize];
			System.arraycopy(paths
					filtersize);
			pf = PathFilterGroup.createFromStrings(filterPaths);
		}
		long t3 = System.nanoTime();
		data = "PX\t" + (t2 - t1) / 1E9 + "\t" + (t3 - t2) / 1E9 + "\t";
		tInit = t2-t1;
	}

	public void test(int pathSize
			throws MissingObjectException
			IOException {
		setup(pathSize
		data += pathSize + "\t" + filterSize + "\t";
		long t1 = System.nanoTime();
		TreeWalk tw = new TreeWalk((ObjectReader) null);
		tw.reset();
		tw.addTree(new DirCacheIterator(dc));
		tw.setFilter(pf);
		tw.setRecursive(true);
		int n = 0;
		while (tw.next())
			n++;
		long t2 = System.nanoTime();
		data += (t2 - t1) / 1E9;
		System.out.println(data);
		assertEquals(filterSize
	}

	@SuppressWarnings("boxing")
	public void test(int pathSize
			throws MissingObjectException
			IOException {
		setup(pathSize
		data += pathSize + "\t" + filters.length + "\t";
		long t1 = System.nanoTime();
		TreeWalk tw = new TreeWalk((ObjectReader) null);
		tw.reset();
		tw.addTree(new DirCacheIterator(dc));
		tw.setFilter(pf);
		tw.setRecursive(true);
		int n = 0;
		while (tw.next())
			n++;
		long t2 = System.nanoTime();
		data += (t2 - t1) / 1E9;
		System.out.println(data);
		assertEquals(expectedWalkCount

		assertThat(t2 - t1
	}

	@Test
	public void test1_1() throws MissingObjectException
			IncorrectObjectTypeException
		test(1
	}

	@Test
	public void test2_2() throws MissingObjectException
			IncorrectObjectTypeException
		test(2
	}

	@Test
	public void test10_10() throws MissingObjectException
			IncorrectObjectTypeException
		test(10
	}

	@Test
	public void test100_100() throws MissingObjectException
			IncorrectObjectTypeException
		test(100
	}

	@Test
	public void test1000_1000() throws MissingObjectException
			IncorrectObjectTypeException
		test(1000
	}

	@Test
	public void test10000_10000() throws MissingObjectException
			IncorrectObjectTypeException
		test(10000
	}

	@Test
	public void test100000_100000() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test100000_10000() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test100000_1000() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test100000_100() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test100000_10() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test100000_1() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test10000_1F() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test10000_L2() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}

	@Test
	public void test10000_L10() throws MissingObjectException
			IncorrectObjectTypeException
		test(100000
	}
}
