			IMarker marker = markerDelta.getMarker();
			switch (markerDelta.getKind()) {
			case IResourceDelta.ADDED:
				if (markerDelta.isSubtypeOf(IMarker.BOOKMARK)) {
					additions.add(marker);
				}
				break;
			case IResourceDelta.REMOVED:
				if (markerDelta.isSubtypeOf(IMarker.BOOKMARK)) {
					removals.add(marker);
				}
				break;
			case IResourceDelta.CHANGED:
				if (markerDelta.isSubtypeOf(IMarker.BOOKMARK)) {
					changes.add(marker);
				}
				break;
			}
		}

