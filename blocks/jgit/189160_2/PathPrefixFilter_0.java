
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

public class PathPrefixFilterTest extends RepositoryTestCase {

	@Test
	public void testNonRecursiveFiltering() throws IOException {
		ObjectId treeId = createTree("a.sth"

		List<String> paths = getMatchingPaths("a.sth"
		List<String> expected = Arrays.asList("a.sth");

		assertEquals(expected
	}

	@Test
	public void testSubdirectoryFiltering() throws IOException {
		ObjectId treeId = createTree("a.sth"
				"foo/bar/c.txt"

		List<String> paths = getMatchingPaths("foo"
		List<String> expected = Arrays.asList("foo/b.sth"

		assertEquals(expected
	}

	@Test
	public void testNestedSubdirectoryFiltering() throws IOException {
		ObjectId treeId = createTree("a.sth"
				"foo/bar/c.txt"

		List<String> paths = getMatchingPaths("foo/bar"
		List<String> expected = Arrays.asList("foo/bar/c.txt");

		assertEquals(expected
	}

	@Test
	public void testNegated() throws IOException {
		ObjectId treeId = createTree("a.sth"

		List<String> paths = getMatchingPaths("a"
		List<String> expected = Arrays.asList();

		assertEquals(expected
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
		return getMatchingPaths(suffixFilter
	}

	private List<String> getMatchingPaths(String suffixFilter
			final ObjectId treeId
			throws IOException {
		try (TreeWalk tw = new TreeWalk(db)) {
			TreeFilter filter = PathPrefixFilter.create(suffixFilter);

			if (negated) {
				filter = filter.negate();
			}

			tw.setFilter(filter);
			tw.setRecursive(recursiveWalk);
			tw.addTree(treeId);

			List<String> paths = new ArrayList<>();

			while (tw.next()) {
				paths.add( tw.getPathString() );
			}

			return paths;
		}
	}

}
