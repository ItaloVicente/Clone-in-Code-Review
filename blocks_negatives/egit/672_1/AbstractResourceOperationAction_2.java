			} catch (Throwable e) {
				final String msg = NLS.bind(UIText.GenericOperationFailed, act
						.getText());
				final IStatus status;

				if (e instanceof InvocationTargetException) {
					e = e.getCause();
				}

				if (e instanceof CoreException) {
					status = ((CoreException) e).getStatus();
				} else {
					status = new Status(IStatus.ERROR, Activator.getPluginId(),
							1, msg, e);
				}

				Activator.logError(msg, e);
				ErrorDialog.openError(wp.getSite().getShell(), act.getText(),
						msg, status, status.getSeverity());
