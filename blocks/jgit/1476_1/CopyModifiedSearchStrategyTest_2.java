package org.eclipse.jgit.blame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class CommonChunkCalculationTest extends TestCase {
	public void testNoDifference() throws Exception {
		int length = 100;
		List<MockDifference> differences = new ArrayList<MockDifference>();
		List<CommonChunk> expected = Arrays
				.asList(new CommonChunk(0
		assertComputation(differences
	}

	public void testSimpleCommonPrefix() throws Exception {
		int length = 100;
		List<MockDifference> differences = Arrays.asList(new MockDifference(3
				length
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(0
		assertComputation(differences
	}

	public void testCommonPrefix() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(3
				10
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(0
		assertComputation(differences
	}

	public void testSimpleCommonSuffix() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(0
				10
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(11
		assertComputation(differences
	}

	public void testSimpleInfix() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(0
				10
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(11
		assertComputation(differences
	}

	public void testSimpleAdditionFront() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(0
				12
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(13
		assertComputation(differences
	}

	public void testSimpleAdditionMiddle() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(10
				12
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(0
				new CommonChunk(13
		assertComputation(differences
	}

	public void testSimpleAdditionEnd() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(10
				12
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(0
		assertComputation(differences
	}

	public void testSimpleAdditionTwoDifferences() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(3
				-1
		List<CommonChunk> expected = Arrays.asList(new CommonChunk(0
				new CommonChunk(3
		assertComputation(differences
	}

	public void testUnequalDiffLengths() throws Exception {
		List<MockDifference> differences = Arrays.asList(new MockDifference(0
				1
		try {
			CommonChunk.computeCommonChunks(differences
			fail("Should fail for unequal diff lengths");
		} catch (RuntimeException e) {
		}

		differences = Arrays.asList(new MockDifference(0
		try {
			CommonChunk.computeCommonChunks(differences
			fail("Should fail for unequal diff lengths");
		} catch (RuntimeException e) {
		}

		differences = Arrays.asList(new MockDifference(1
		try {
			CommonChunk.computeCommonChunks(differences
			fail("Should fail for unequal diff lengths");
		} catch (RuntimeException e) {
		}
	}

	private void assertComputation(List<MockDifference> differences
			List<CommonChunk> expected
		{
			List<CommonChunk> commonChunks = CommonChunk.computeCommonChunks(
					differences
			assertEquals(expected
		}
		{
			List<MockDifference> invertedDifferences = new ArrayList<MockDifference>();
			for (MockDifference difference : differences) {
				invertedDifferences.add(difference.inverted());
			}
			List<CommonChunk> invertedExpected = new ArrayList<CommonChunk>();
			for (CommonChunk commonChunk : expected) {
				invertedExpected.add(new CommonChunk(commonChunk.getBstart()
						commonChunk.getAstart()
			}
			List<CommonChunk> commonChunks = CommonChunk.computeCommonChunks(
					invertedDifferences
			assertEquals(invertedExpected
		}
	}
}
