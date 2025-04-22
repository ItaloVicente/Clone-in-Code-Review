					Policy
							.getLog()
							.log(
									new Status(
											IStatus.ERROR,
											Policy.JFACE_DATABINDING,
											IStatus.OK,
											"Unhandled exception: " + exception.getMessage(), exception)); //$NON-NLS-1$
