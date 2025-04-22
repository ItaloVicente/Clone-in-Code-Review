
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class HttpConfigUriPathTest {

	@Test
	public void testNormalizationEmptyPaths() {
		assertEquals("/"
		assertEquals("/"
	}

	@Test
	public void testNormalization() {
		assertEquals("/f"
		assertEquals("/f"
		assertEquals("/f/"
		assertEquals("/foo"
		assertEquals("/foo"
		assertEquals("/foo/"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar/"
	}

	@Test
	public void testNormalizationWithDot() {
		assertEquals("/"
		assertEquals("/"
		assertEquals("/"
		assertEquals("/foo"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar/"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar/"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar/"
		assertEquals("/foo/bar/.baz/bam"
				HttpConfig.normalize("/foo/bar/.baz/bam"));
		assertEquals("/foo/bar/.baz/bam/"
				HttpConfig.normalize("/foo/bar/.baz/bam/"));
	}

	@Test
	public void testNormalizationWithDotDot() {
		assertEquals("/"
		assertEquals("/"
		assertEquals("/"
		assertEquals("/"
		assertEquals("/bar"
		assertEquals("/bar"
		assertEquals("/bar"
		assertEquals("/bar/"
		assertEquals("/bar/"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar/"
		assertEquals("/foo"
		assertEquals("/foo"
		assertEquals("/foo"
		assertEquals("/foo"
		assertEquals("/foo/baz"
		assertEquals("/foo/baz/"
		assertEquals("/foo/baz"
		assertEquals("/foo/baz/"
		assertEquals("/foo"
		assertEquals("/foo/"
		assertEquals("/baz"
		assertEquals("/baz/"
		assertEquals("/foo/.b/bar"
		assertEquals("/.f/foo/.b/bar/"
		assertEquals("/foo/bar/..baz/bam"
				HttpConfig.normalize("/foo/bar/..baz/bam"));
		assertEquals("/foo/bar/..baz/bam/"
				HttpConfig.normalize("/foo/bar/..baz/bam/"));
		assertEquals("/foo/bar/.../baz/bam"
				HttpConfig.normalize("/foo/bar/.../baz/bam"));
		assertEquals("/foo/bar/.../baz/bam/"
				HttpConfig.normalize("/foo/bar/.../baz/bam/"));
	}

	@Test
	public void testNormalizationWithDoubleSlash() {
		assertEquals("/"
		assertEquals("/foo/"
		assertEquals("/foo"
		assertEquals("/foo/"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar"
		assertEquals("/foo/bar/"
	}

	@Test
	public void testNormalizationWithDotDotFailing() {
		assertNull(HttpConfig.normalize(".."));
		assertNull(HttpConfig.normalize("/.."));
		assertNull(HttpConfig.normalize("/../"));
		assertNull(HttpConfig.normalize("/../foo"));
		assertNull(HttpConfig.normalize("./../foo"));
		assertNull(HttpConfig.normalize("/./../foo"));
		assertNull(HttpConfig.normalize("/foo/./.././.."));
		assertNull(HttpConfig.normalize("/foo/../bar/../.."));
		assertNull(HttpConfig.normalize("/foo/../bar/../../baz"));
	}

	@Test
	public void testSegmentCompare() {
		assertSuccess("/foo"
		assertSuccess("/foo"
		assertSuccess("/foo"
		assertSuccess("/foo"
		assertSuccess("/foo"
		assertSuccess("/foo/"
		assertSuccess("/foo/"
		assertSuccess("/foo/"
		assertSuccess("/foo/"
		assertSuccess("/foo/bar"
		assertSuccess("/foo/bar"
		assertSuccess("/foo/bar"
		assertSuccess("/foo/bar/"
		assertSuccess("/foo/bar/"
		assertSuccess("/foo/bar"
		assertSuccess("/foo/bar/"
		assertSuccess("/foo/bar/"
		assertSuccess("/foo/bar"
		assertSuccess("/foo/bar/"
		assertSuccess("/foo/bar/"
		assertSuccess("/foo/bar"
		assertSuccess("/foo/bar/"
		assertSuccess("/some/repo/.git"
		assertSuccess("/some/repo/bare.git"
		assertSuccess("/some/repo/.git"
		assertSuccess("/some/repo/bare.git"
	}

	@Test
	public void testSegmentCompareFailing() {
		assertEquals(-1
		assertEquals(-1
		assertEquals(-1
		assertEquals(-1
		assertEquals(-1
				HttpConfig.segmentCompare("/foo/barbar/baz"
		assertEquals(-1
		assertEquals(-1
				HttpConfig.segmentCompare("/some/repo.git"
		assertEquals(-1
				HttpConfig.segmentCompare("/some/repo.git"
		assertEquals(-1
				"/some/repo/bar"));
		assertSuccess("/some/repo/bare.git"
		assertEquals(-1
		assertEquals(-1
				HttpConfig.segmentCompare("/foo/barbar/baz"
		assertEquals(-1
				HttpConfig.segmentCompare("/foo/barbar/baz"
		assertEquals(-1
		assertEquals(-1
		assertEquals(-1
		assertEquals(-1
				HttpConfig.segmentCompare("/foo/bar/baz"
	}

	private void assertSuccess(String uri
		String normalized = HttpConfig.normalize(match);
		assertEquals(normalized.length()
				HttpConfig.segmentCompare(uri
	}
}
