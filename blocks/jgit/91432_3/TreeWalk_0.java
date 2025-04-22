package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PathFilterLogicTest extends RepositoryTestCase {

	private ObjectId treeId;

	@Before
	public void setup() throws IOException {
		String[] paths = new String[] {
				"a.txt"
				"sub1.txt"
				"sub1/suba/a.txt"
				"sub1/subb/b.txt"
				"sub2/suba/a.txt"
		};
		treeId = createTree(paths);
	}

	@Test
	public void testSinglePath() throws IOException {
		List<String> expected = Arrays.asList("sub1/suba/a.txt"
				"sub1/subb/b.txt");

		TreeFilter tf = PathFilter.create("sub1");
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testSingleSubPath() throws IOException {
		List<String> expected = Collections.singletonList("sub1/suba/a.txt");

		TreeFilter tf = PathFilter.create("sub1/suba");
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testSinglePathNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"
				"sub2/suba/a.txt");

		TreeFilter tf = PathFilter.create("sub1").negate();
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testSingleSubPathNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"
				"sub1/subb/b.txt"

		TreeFilter tf = PathFilter.create("sub1/suba").negate();
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testOrMultiTwoPath() throws IOException {
		List<String> expected = Arrays.asList("sub1/suba/a.txt"
				"sub1/subb/b.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1")
				PathFilter.create("sub2")};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testOrMultiThreePath() throws IOException {
		List<String> expected = Arrays.asList("sub1.txt"
				"sub1/subb/b.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1")
				PathFilter.create("sub2")
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testOrMultiTwoSubPath() throws IOException {
		List<String> expected = Arrays.asList("sub1/subb/b.txt"
				"sub2/suba/a.txt");

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1/subb")
				PathFilter.create("sub2/suba")};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testOrMultiTwoMixSubPath() throws IOException {
		List<String> expected = Arrays.asList("sub1/subb/b.txt"
				"sub2/suba/a.txt");

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1/subb")
				PathFilter.create("sub2")};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testOrMultiTwoMixSubPathNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"
				"sub1/suba/a.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1").negate()
				PathFilter.create("sub1/suba")};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testOrMultiThreeMixSubPathNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"
				"sub1/suba/a.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1").negate()
				PathFilter.create("sub1/suba")
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testPatternParentFileMatch() throws IOException {
		List<String> expected = Collections.emptyList();

		TreeFilter tf = PathFilter.create("a.txt/test/path");
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testAndMultiPath() throws IOException {
		List<String> expected = Collections.emptyList();

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1")
				PathFilter.create("sub2")};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testAndMultiPathNegate() throws IOException {
		List<String> expected = Arrays.asList("sub1/suba/a.txt"
				"sub1/subb/b.txt");

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1")
				PathFilter.create("sub2").negate()};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testAndMultiSubPathDualNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"
				"sub1/subb/b.txt");

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1/suba").negate()
				PathFilter.create("sub2").negate()};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testAndMultiSubPath() throws IOException {
		List<String> expected = Collections.emptyList();

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1")
				PathFilter.create("sub2/suba")};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testAndMultiSubPathNegate() throws IOException {
		List<String> expected = Collections.singletonList("sub1/subb/b.txt");

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1")
				PathFilter.create("sub1/suba").negate()};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testAndMultiThreeSubPathNegate() throws IOException {
		List<String> expected = Collections.singletonList("sub1/subb/b.txt");

		TreeFilter[] tf = new TreeFilter[]{PathFilter.create("sub1")
				PathFilter.create("sub1/suba").negate()
				PathFilter.create("no/path").negate()};
		List<String> paths = getMatchingPaths(treeId

		assertEquals(expected
	}

	@Test
	public void testTopAndMultiPathDualNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1").negate()
				PathFilter.create("sub2").negate()};
		List<String> paths = getMatchingPathsFlat(treeId

		assertEquals(expected
	}

	@Test
	public void testTopAndMultiSubPathDualNegate() throws IOException {
		List<String> expected = Arrays.asList("a.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1/suba").negate()
				PathFilter.create("sub2").negate()};
		List<String> paths = getMatchingPathsFlat(treeId

		assertEquals(expected
	}

	@Test
	public void testTopOrMultiPathDual() throws IOException {
		List<String> expected = Arrays.asList("sub1.txt"

		TreeFilter[] tf = new TreeFilter[] {PathFilter.create("sub1.txt")
				PathFilter.create("sub2")};
		List<String> paths = getMatchingPathsFlat(treeId

		assertEquals(expected
	}

	@Test
	public void testTopNotPath() throws IOException {
		List<String> expected = Arrays.asList("a.txt"

		TreeFilter tf = PathFilter.create("sub1");
		List<String> paths = getMatchingPathsFlat(treeId

		assertEquals(expected
	}

	private List<String> getMatchingPaths(final ObjectId treeId
			TreeFilter tf) throws IOException {
		return _getMatchingPaths(treeId
	}

	private List<String> getMatchingPathsFlat(final ObjectId treeId
			TreeFilter tf) throws IOException {
		return _getMatchingPaths(treeId
	}

	private List<String> _getMatchingPaths(final ObjectId treeId
			TreeFilter tf
		TreeWalk tw = new TreeWalk(db);
		tw.setFilter(tf);
		tw.setRecursive(recursive);
		tw.addTree(treeId);

		List<String> paths = new ArrayList<>();
		while(tw.next()) {
			paths.add(tw.getPathString());
		}
		return paths;
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
}

