		} else {
			String name = BRANCH_NAME_PATTERN.matcher(rev).replaceAll(""); //$NON-NLS-1$
			String remoteTracking = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, CONFIG_KEY_REMOTE);
			if (remoteTracking != null && remoteTracking.length() > 0)
				return remoteTracking;
			else
				return null;
		}
