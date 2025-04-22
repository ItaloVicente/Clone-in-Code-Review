				|| !cachedPacks.isEmpty());

		if (compressMonitor instanceof BatchingProgressMonitor) {
			long delay = 1000;
			if (needSearchForReuse && config.isDeltaCompress())
				delay = 500;
			((BatchingProgressMonitor) compressMonitor).setDelayStart(
					delay
					TimeUnit.MILLISECONDS);
		}

		if (needSearchForReuse)
