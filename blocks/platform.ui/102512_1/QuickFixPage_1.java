				int relevanceMarker1 = (e1 instanceof IMarkerResolutionRelevance)
						? ((IMarkerResolutionRelevance) e1).getRelevanceForResolution()
						: 0;
				int relevanceMarker2 = (e2 instanceof IMarkerResolutionRelevance)
						? ((IMarkerResolutionRelevance) e2).getRelevanceForResolution()
						: 0;
				if (relevanceMarker1 != relevanceMarker2) {
					return Integer.valueOf(relevanceMarker2).compareTo(Integer.valueOf(relevanceMarker1));
				}
