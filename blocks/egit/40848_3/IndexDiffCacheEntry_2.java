
		if (shouldReload(filesToUpdate)) {
			scheduleReloadJob("Too many resources changed: " + filesToUpdate.size()); //$NON-NLS-1$
			return;
		}

