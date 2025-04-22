				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(),
							"New member id for " + repoRelativePath //$NON-NLS-1$
									+ ": " + newMember.getId() + " idx id: " //$NON-NLS-1$ //$NON-NLS-2$
									+ idxEntry.getObjectId());
