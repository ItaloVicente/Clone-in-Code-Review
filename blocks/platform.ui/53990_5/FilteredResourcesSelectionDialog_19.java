			IPath p1 = resource1.getFullPath();
			IPath p2 = resource2.getFullPath();
			int c12 = p1.segmentCount() - 1;
			int c22 = p2.segmentCount() - 1;
			for (int i= 0; i < c12 && i < c22; i++) {
				comparability = collator.compare(p1.segment(i), p2.segment(i));
				if (comparability != 0)
					return comparability;
