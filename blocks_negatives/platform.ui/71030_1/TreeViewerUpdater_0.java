				for (int j = 0; j < path.getSegmentCount() - 1; j++) {
					Object pathParent = path.getSegment(j);
					Object pathElement = path.getSegment(j + 1);
					if (eq(comparer, parent, pathParent)
							&& eq(comparer, element, pathElement)) {
						return true;
					}
