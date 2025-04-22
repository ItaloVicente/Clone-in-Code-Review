		if (branchName != null) {
			destinationString = MessageFormat.format("{0} {1} - {2}", //$NON-NLS-1$
					repository.getDirectory().getParentFile().getName(),
					branchName, config.getName());
		} else {
			destinationString = MessageFormat.format("{0} - {1}", //$NON-NLS-1$
					repository.getDirectory().getParentFile().getName(),
					config.getName());
		}
