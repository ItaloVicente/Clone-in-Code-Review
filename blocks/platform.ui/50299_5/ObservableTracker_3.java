			Policy.getLog()
					.log(new Status(
							IStatus.ERROR,
							Policy.JFACE_DATABINDING,
							"There were " //$NON-NLS-1$
									+ currentIgnoreCount.get()
									+ " unmatched setIgnore(true) invocations in runnable " //$NON-NLS-1$
									+ runnable));
