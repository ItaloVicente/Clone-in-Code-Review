				if (e1 instanceof IMarkerRelevance && e2 instanceof IMarkerRelevance) {
					int relevanceMarker1 = ((IMarkerRelevance) e1).getRelevanceForMarker();
					int relevanceMarker2 = ((IMarkerRelevance) e2).getRelevanceForMarker();
					if (relevanceMarker1 != relevanceMarker2) {
						return Integer.valueOf(relevanceMarker1).compareTo(Integer.valueOf(relevanceMarker1));
					}
				}
