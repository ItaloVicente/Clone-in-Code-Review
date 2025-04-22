
package org.eclipse.jgit.treewalk.filter;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class PathSuffixFilterTest extends RepositoryTestCase {

	@Test
	public void testNonRecursiveFiltering() throws IOException {
		ObjectId treeId = createTree("a.sth"

		List<String> paths = getMatchingPaths(".txt"
		List<String> expected = Arrays.asList("a.txt");

		assertEquals(expected
	}

	@Test
	public void testRecursiveFiltering() throws IOException {
		ObjectId treeId = createTree("a.sth"

		List<String> paths = getMatchingPaths(".txt"
		List<String> expected = Arrays.asList("a.txt"

		assertEquals(expected
	}

	@Test
	public void testEdgeCases() throws IOException {
		ObjectId treeId = createTree("abc"
		assertEquals(new ArrayList<String>()
		assertEquals(new ArrayList<String>()
		assertEquals(Arrays.asList("abcd")
		assertEquals(Arrays.asList("abcd"
		assertEquals(Arrays.asList("abc"
	}

	private ObjectId createTree(String... paths) throws IOException {
		final ObjectInserter odi = db.newObjectInserter();
		final DirCache dc = db.readDirCache();
		final DirCacheBuilder builder = dc.builder();
		for (String path : paths) {
			DirCacheEntry entry = createEntry(path
			builder.add(entry);
		}
		builder.finish();
		final ObjectId treeId = dc.writeTree(odi);
		odi.flush();
		return treeId;
	}

	private List<String> getMatchingPaths(String suffixFilter
			final ObjectId treeId) throws IOException {
		return getMatchingPaths(suffixFilter
	}

	private List<String> getMatchingPaths(String suffixFilter
			final ObjectId treeId
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setFilter(PathSuffixFilter.create(suffixFilter));
			tw.setRecursive(recursiveWalk);
			tw.addTree(treeId);

			List<String> paths = new ArrayList<>();
			while (tw.next())
				paths.add(tw.getPathString());
			return paths;
		}
	}

}
