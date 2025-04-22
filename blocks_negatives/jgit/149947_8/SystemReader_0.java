		} else {
			getSystemConfig();
		}
		if (c.isOutdated()) {
			LOG.debug("loading user config {}", userConfig); //$NON-NLS-1$
			c.load();
