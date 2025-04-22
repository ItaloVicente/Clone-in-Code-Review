package org.eclipse.jgit.lines;

import java.util.Comparator;

public class LineComparator implements Comparator<Line> {

	public int compare(Line line1
		if (line1.overlaps(line2)) {
			int revision = Math.min(line1.getEnd()
			return line1.getNumber(revision) - line2.getNumber(revision);
		} else
			return line1.getStart() - line2.getStart();
	}
}
