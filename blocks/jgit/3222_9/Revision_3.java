package org.eclipse.jgit.blame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class LineComparator implements Comparator<Line> {

	public static Set<Line> sort(Iterable<Revision> revisions) {
		Set<Line> lines = new TreeSet<Line>(new LineComparator());
		for (Revision revision : revisions)
			lines.addAll(Arrays.asList(revision.getLines()));
		return lines;
	}

	public int compare(Line line1
		if (line1.overlaps(line2)) {
			int revision = Math.min(line1.getEnd()
			return line1.getNumber(revision) - line2.getNumber(revision);
		} else
			return line1.getStart() - line2.getStart();
	}
}
