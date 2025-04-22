		if (config.isFetchOnFeatureStart()) {
			try {
				fetch(progress.newChild(1), timeoutInSeconds);
			} catch (InvocationTargetException e) {
				throw new CoreException(Activator.error(e));
			} catch (URISyntaxException e) {
				throw new CoreException(Activator.error(e));
			}
		}
		progress.setWorkRemaining(1);
