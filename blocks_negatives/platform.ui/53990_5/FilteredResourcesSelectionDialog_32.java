				IPath p1 = resource1.getFullPath();
				IPath p2 = resource2.getFullPath();
				int c1 = p1.segmentCount() - 1;
				int c2 = p2.segmentCount() - 1;
				for (int i= 0; i < c1 && i < c2; i++) {
					comparability = collator.compare(p1.segment(i), p2.segment(i));
					if (comparability != 0)
						return comparability;
				}
				comparability = c1 - c2;
