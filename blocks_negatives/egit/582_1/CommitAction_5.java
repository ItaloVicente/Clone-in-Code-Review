				newMember.setId(idxEntry.getObjectId());
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(),
									+ idxEntry.getObjectId());
			}
		}
	}
