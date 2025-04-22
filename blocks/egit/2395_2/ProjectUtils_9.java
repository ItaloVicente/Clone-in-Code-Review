		try {
			ResourcesPlugin.getWorkspace().run(wsr, monitor);
		} catch (OperationCanceledException e) {
			throw new InterruptedException();
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		}
