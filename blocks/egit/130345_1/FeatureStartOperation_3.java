		if (config.isFetchOnFeatureStart()) {
			try {
				fetch(progress.newChild(1), timeout);
			} catch (InvocationTargetException e) {
				throw new CoreException(Activator.error(e));
			}
		}
