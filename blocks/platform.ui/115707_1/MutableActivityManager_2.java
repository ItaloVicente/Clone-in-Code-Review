
					boolean activityIdsChanged = identifier.setActivityIds(activityIds);
					if (activityIdsChanged) {
						IdentifierEvent identifierEvent = new IdentifierEvent(identifier, activityIdsChanged, false);
						identifierEventsByIdentifierId.put(identifier.getId(), identifierEvent);
					}
				}
				if (!identifierEventsByIdentifierId.isEmpty()) {
					UIJob notifyJob = new UIJob("Activity Identifier Update UI") { //$NON-NLS-1$
						@Override
						public IStatus runInUIThread(IProgressMonitor monitor) {
							notifyIdentifiers(identifierEventsByIdentifierId);
							return Status.OK_STATUS;
						}
					};
					notifyJob.setSystem(true);
					notifyJob.schedule();
				}
				return Status.OK_STATUS;
