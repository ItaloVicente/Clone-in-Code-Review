
		if (filesToUpdate.size() > RESOURCE_LIST_UPDATE_LIMIT) {
			scheduleReloadJob("Too many resources changed"); //$NON-NLS-1$
			return;
		}

