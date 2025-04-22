			});
		} catch (InvocationTargetException e) {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
			throw e;
		} catch (InterruptedException e) {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
			throw new InvocationTargetException(e);
		}
