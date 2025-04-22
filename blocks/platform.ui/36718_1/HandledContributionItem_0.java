			if (cmdId == null) {
				Activator.log(IStatus.ERROR, "Unable to generate parameterized command for " + model //$NON-NLS-1$
						+ ". ElementId is not allowed to be null."); //$NON-NLS-1$
				return;
			}

