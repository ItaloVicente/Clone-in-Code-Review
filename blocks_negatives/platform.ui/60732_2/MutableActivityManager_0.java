
								@Override
								public IStatus runInUIThread(
										IProgressMonitor monitor) {
									notifyIdentifiers(identifierEventsByIdentifierId);
									return Status.OK_STATUS;
								}
                            };
                            notifyJob.setSystem(true);
                            notifyJob.schedule();
                        }
