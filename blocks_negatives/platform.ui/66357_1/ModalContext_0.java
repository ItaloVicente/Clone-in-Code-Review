						Policy
								.getLog()
								.log(
										new Status(
												IStatus.ERROR,
												Policy.JFACE,
												"Unhandled event loop exception during blocked modal context.",//$NON-NLS-1$
												t));
