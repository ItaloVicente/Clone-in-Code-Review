package org.eclipse.jgit.treewalk;

import java.time.Instant;
import java.util.Comparator;

class InstantComparator implements Comparator<Instant> {

	@Override
	public int compare(Instant a
		return compare(a
	}

	public int compare(Instant a
		long aSeconds = a.getEpochSecond();
		long bSeconds = b.getEpochSecond();
		int result = Long.compare(aSeconds
		if (result != 0) {
			return result;
		}
		int aSubSecond = a.getNano();
		int bSubSecond = b.getNano();
		if (forceSecondsOnly || (aSubSecond == 0)
				|| (bSubSecond == 0)) {
			return 0;
		} else if (aSubSecond != bSubSecond) {
			int aSubMillis = aSubSecond % 1_000_000;
			int bSubMillis = bSubSecond % 1_000_000;
			if (aSubMillis == 0) {
				bSubSecond -= bSubMillis;
			} else if (bSubMillis == 0) {
				aSubSecond -= aSubMillis;
			} else {
				int aSubMicros = aSubSecond % 1000;
				int bSubMicros = bSubSecond % 1000;
				if (aSubMicros == 0) {
					bSubSecond -= bSubMicros;
				} else if (bSubMicros == 0) {
					aSubSecond -= aSubMicros;
				}
			}
		}
		return Integer.compare(aSubSecond
	}

}
