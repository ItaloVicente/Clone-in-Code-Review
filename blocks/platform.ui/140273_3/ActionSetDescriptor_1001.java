		if (label == null) {
			throw new CoreException(new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, 0,
					"Invalid extension (missing label): " + id, //$NON-NLS-1$
					null));
		}
	}

	@Override
