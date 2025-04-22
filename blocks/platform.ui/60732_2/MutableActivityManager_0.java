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
