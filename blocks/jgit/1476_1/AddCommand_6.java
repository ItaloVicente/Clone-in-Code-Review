package org.eclipse.jgit.blame;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class SplitOverlapTest extends TestCase {

	public void testPerfectMatch() throws Exception {
		Origin parent = new Origin(null
		Origin target = new Origin(null

		BlameEntry blameEntry = new BlameEntry();
		blameEntry.originalRange = new Range(17
		blameEntry.suspectStart = 50;
		blameEntry.suspect = target;
		List<BlameEntry> actual = Scoreboard.splitOverlap(blameEntry
				new CommonChunk(70
		BlameEntry expected1 = new BlameEntry();
		expected1.originalRange = new Range(17
		expected1.suspectStart = 70;
		expected1.suspect = parent;
		List<BlameEntry> expected = Arrays.asList(expected1);
		assertEquals(expected
	}

	public void testSplitMiddle() throws Exception {
		Origin parent = new Origin(null
		Origin target = new Origin(null

		BlameEntry blameEntry = new BlameEntry();
		blameEntry.originalRange = new Range(17
		blameEntry.suspectStart = 50;
		blameEntry.suspect = target;
		List<BlameEntry> actual = Scoreboard.splitOverlap(blameEntry
				new CommonChunk(73
		BlameEntry expected1 = new BlameEntry();
		expected1.originalRange = new Range(17
		expected1.suspectStart = 50;
		expected1.suspect = target;
		BlameEntry expected2 = new BlameEntry();
		expected2.originalRange = new Range(20
		expected2.suspectStart = 73;
		expected2.suspect = parent;
		BlameEntry expected3 = new BlameEntry();
		expected3.originalRange = new Range(24
		expected3.suspectStart = 57;
		expected3.suspect = target;
		List<BlameEntry> expected = Arrays.asList(expected1
				expected3);
		assertEquals(expected
	}

	public void testSplitCommonChunkBigger() throws Exception {
		Origin parent = new Origin(null
		Origin target = new Origin(null

		BlameEntry blameEntry = new BlameEntry();
		blameEntry.originalRange = new Range(17
		blameEntry.suspectStart = 50;
		blameEntry.suspect = target;
		List<BlameEntry> actual = Scoreboard.splitOverlap(blameEntry
				new CommonChunk(73
		BlameEntry expected1 = new BlameEntry();
		expected1.originalRange = new Range(17
		expected1.suspectStart = 83;
		expected1.suspect = parent;
		List<BlameEntry> expected = Arrays.asList(expected1);
		assertEquals(expected
	}

	public void testSplitCommonChunkStartsBefore() throws Exception {
		Origin parent = new Origin(null
		Origin target = new Origin(null

		BlameEntry blameEntry = new BlameEntry();
		blameEntry.originalRange = new Range(17
		blameEntry.suspectStart = 50;
		blameEntry.suspect = target;
		List<BlameEntry> actual = Scoreboard.splitOverlap(blameEntry
				new CommonChunk(73
		BlameEntry expected1 = new BlameEntry();
		expected1.originalRange = new Range(17
		expected1.suspectStart = 83;
		expected1.suspect = parent;
		BlameEntry expected2 = new BlameEntry();
		expected2.originalRange = new Range(27
		expected2.suspectStart = 60;
		expected2.suspect = target;
		List<BlameEntry> expected = Arrays.asList(expected1
		assertEquals(expected
	}

	public void testSplitCommonChunkEndsAfter() throws Exception {
		Origin parent = new Origin(null
		Origin target = new Origin(null

		BlameEntry blameEntry = new BlameEntry();
		blameEntry.originalRange = new Range(17
		blameEntry.suspectStart = 50;
		blameEntry.suspect = target;
		List<BlameEntry> actual = Scoreboard.splitOverlap(blameEntry
				new CommonChunk(73
		BlameEntry expected1 = new BlameEntry();
		expected1.originalRange = new Range(17
		expected1.suspectStart = 50;
		expected1.suspect = target;
		BlameEntry expected2 = new BlameEntry();
		expected2.originalRange = new Range(22
		expected2.suspectStart = 73;
		expected2.suspect = parent;
		List<BlameEntry> expected = Arrays.asList(expected1
		assertEquals(expected
	}

}
