						} catch (InvocationTargetException e) {
							String msg = NLS.bind(IDEWorkbenchMessages.WorkspaceAction_logTitle, getClass().getName(),
									e.getTargetException());
							throw new CoreException(StatusUtil.newStatus(IStatus.ERROR, msg, e.getTargetException()));
						} catch (InterruptedException e) {
							return Status.CANCEL_STATUS;
						}
						return errorStatus[0];
					}

				};
				ISchedulingRule rule = op.getRule();
				if (rule != null) {
					job.setRule(rule);
				}
				job.setUser(true);
				job.schedule();
			}
		};
		refreshAction.setDisabledImageDescriptor(getImageDescriptor("dlcl16/refresh_nav.png"));//$NON-NLS-1$
		refreshAction.setImageDescriptor(getImageDescriptor("elcl16/refresh_nav.png"));//$NON-NLS-1$
		buildAction = new BuildAction(provider, IncrementalProjectBuilder.INCREMENTAL_BUILD);
	}
