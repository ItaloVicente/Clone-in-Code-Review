					throw e;
				} catch (Exception e) {
					throw new CoreException(
							new Status(
									IStatus.ERROR,
									"org.eclipse.egit.ui", UIText.IgnoreAction_error, e)); //$NON-NLS-1$
