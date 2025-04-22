			IStatus result = createResult(exceptions);
			if (!result.isOK()) {
				throw new CoreException(result);
			}
		} finally {
			monitor.done();
