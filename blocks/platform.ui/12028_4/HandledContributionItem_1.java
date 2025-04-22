			if (parmCmd == null) {
				Activator.log(IStatus.ERROR,
						"Unable to generate parameterized command for " + model //$NON-NLS-1$
								+ " with " + parameters); //$NON-NLS-1$
				return;
			}
