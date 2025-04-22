				if (e1 instanceof IMarkerResolutionRelevance && e2 instanceof IMarkerResolutionRelevance) {
					int relevanceMarker1 = ((IMarkerResolutionRelevance) e1).getRelevanceForResolution();
					int relevanceMarker2 = ((IMarkerResolutionRelevance) e2).getRelevanceForResolution();
					if (relevanceMarker1 != relevanceMarker2) {
						return Integer.valueOf(relevanceMarker2).compareTo(Integer.valueOf(relevanceMarker1));
					}
				}
