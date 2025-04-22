package org.eclipse.jgit.blame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.RawParseUtils;

public class Scoreboard {
	LinkedList<BlameEntry> blameEntries = new LinkedList<BlameEntry>();

	Origin finalOrigin;

	private final IDiff diff;

	private final OriginWalk originWalk;

	public Scoreboard(Origin finalObject
			IDiff diff) {
		super();
		try {
			originWalk = new OriginWalk(finalObject
		} catch (Exception e) {
			throw new RuntimeException("Unable to create origin walk"
		}
		this.finalOrigin = finalObject;
		this.diff = diff;
		BlameEntry blameEntry = new BlameEntry();
		byte[] bytes = finalObject.getBytes();
		IntList lines = RawParseUtils.lineMap(bytes
		blameEntry.originalRange = new Range(0
		blameEntry.suspect = finalObject;
		blameEntry.suspectStart = 0;
		blameEntries.add(blameEntry);
	}

	public List<BlameEntry> assingBlame() {
		try {

			while (true) {
				Origin origin = originWalk.next();
				HashSet<Origin> todos = new HashSet<Origin>();
				boolean done = true;
				for (BlameEntry blameEntry : blameEntries) {
					if (blameEntry.suspect.equals(origin)) {
						todos.add(blameEntry.suspect);
						done = false;
					}
					if (!blameEntry.guilty) {
						done = false;

					}
				}
				if (done) {
				}
				if (origin == null) {
					System.err.println("Internal error: not all guilty
					return blameEntries;
				}
				if (todos.isEmpty()) {
					continue;
				}
				for (Origin todo : todos) {
					passBlame(todo);
				}
				List<BlameEntry> guilty = new ArrayList<BlameEntry>();
				for (BlameEntry blameEntry : blameEntries) {
					if (origin.equals(blameEntry.suspect)) {
						blameEntry.guilty = true;
						guilty.add(blameEntry);
					}
				}
			}
			return blameEntries;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void passBlame(Origin suspect) {
		Origin[] scapegoats = findScapegoats(suspect);
		for (Origin scapegoat : scapegoats) {
			if (suspect.getObjectId().equals(scapegoat.getObjectId())) {
				passWholeBlameToParent(suspect
				return;
			}
		}
		for (Origin scapegoat : scapegoats) {
			passBlameToParent(suspect
		}
	}

	private void passWholeBlameToParent(Origin target
		for (ListIterator<BlameEntry> it = blameEntries.listIterator(); it
				.hasNext();) {
			BlameEntry blameEntry = it.next();
			if (!blameEntry.suspect.equals(target)) {
			}
			blameEntry.suspect = parent;
		}
	}

	private void passBlameToParent(Origin target
		byte[] parentBytes = parent.getBytes();
		byte[] targetBytes = target.getBytes();
		IntList parentLines = RawParseUtils.lineMap(parentBytes
				parentBytes.length);
		IntList targetLines = RawParseUtils.lineMap(targetBytes
				targetBytes.length);
		IDifference[] differences = diff.diff(parentBytes
				targetBytes

		List<CommonChunk> commonChunks = CommonChunk.computeCommonChunks(Arrays
				.asList(differences)
				.size() - 1);
		for (CommonChunk commonChunk : commonChunks) {
			blameChunk(target
		}
	}

	private void blameChunk(Origin target
			CommonChunk commonChunk) {
		for (ListIterator<BlameEntry> it = blameEntries.listIterator(); it
				.hasNext();) {
			BlameEntry blameEntry = it.next();
			if (blameEntry.guilty || !(blameEntry.suspect.equals(target))) {
			}
			if (commonChunk.getBstart() + commonChunk.getLength() <= blameEntry.suspectStart) {
			}
			if (commonChunk.getBstart() < blameEntry.suspectStart
					+ blameEntry.originalRange.length) {
				List<BlameEntry> newBlameEntries = blameOverlap(blameEntry
						parent
				it.remove();
				for (BlameEntry newBlameEntry : newBlameEntries) {
					it.add(newBlameEntry);
				}
			}

		}
	}

	private List<BlameEntry> blameOverlap(BlameEntry blameEntry
			CommonChunk commonChunk) {
		List<BlameEntry> split = splitOverlap(blameEntry
		return split;
	}


	static List<BlameEntry> splitOverlap(BlameEntry blameEntry
			CommonChunk commonChunk) {
		List<BlameEntry> result = new LinkedList<BlameEntry>();
		BlameEntry split = new BlameEntry();
		if (blameEntry.suspectStart < commonChunk.getBstart()) {
			BlameEntry pre = new BlameEntry();
			pre.suspect = blameEntry.suspect;
			pre.suspectStart = blameEntry.suspectStart;
			pre.originalRange = new Range(blameEntry.originalRange.start
					commonChunk.getBstart() - blameEntry.suspectStart);
			result.add(pre);
			split.originalRange = new Range(blameEntry.originalRange.start
					+ (commonChunk.getBstart() - blameEntry.suspectStart)
			split.suspectStart = commonChunk.getAstart();
		} else {
			split.originalRange = new Range(blameEntry.originalRange.start
			split.suspectStart = commonChunk.getAstart()
					+ (blameEntry.suspectStart - commonChunk.getBstart());
		}

		split.suspect = parent;
		result.add(split);

		int same = commonChunk.getBstart() + commonChunk.getLength();
		int chunkEnd;
		if (same < blameEntry.suspectStart + blameEntry.originalRange.length) {
			BlameEntry post = new BlameEntry();
			post.suspect = blameEntry.suspect;
			int shiftStart = same - blameEntry.suspectStart;
			int numLines = blameEntry.suspectStart
					+ blameEntry.originalRange.length - same;
			post.originalRange = new Range(blameEntry.originalRange.start
					+ shiftStart
			post.suspectStart = blameEntry.suspectStart + shiftStart;
			result.add(post);
			chunkEnd = post.originalRange.start;
		} else {
			chunkEnd = blameEntry.originalRange.start
					+ blameEntry.originalRange.length;
		}
		split.originalRange.length = chunkEnd - split.originalRange.start;

		int sum = 0;
		for (BlameEntry each : result) {
			sum += each.originalRange.length;
		}
		if (sum != blameEntry.originalRange.length) {
			throw new RuntimeException("Internal error splitting blameentries");
		}
		return result;
	}

	Origin[] findScapegoats(Origin origin) {
		return originWalk.getAncestorOrigins();
	}
}
