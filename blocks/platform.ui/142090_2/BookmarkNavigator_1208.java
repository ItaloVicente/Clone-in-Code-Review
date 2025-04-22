				try {
					long id = Long.parseLong(markerMem.getString(TAG_ID));
					IResource resource = root.findMember(markerMem
							.getString(TAG_RESOURCE));
					if (resource != null) {
						IMarker marker = resource.findMarker(id);
						if (marker != null) {
