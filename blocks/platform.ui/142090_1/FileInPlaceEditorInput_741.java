			IResourceDelta mainDelta = event.getDelta();
			if (mainDelta != null && embeddedEditor != null) {
				IResourceDelta affectedElement = mainDelta.findMember(getFile()
						.getFullPath());
				if (affectedElement != null) {
					processDelta(affectedElement);
				}
			}
		}
