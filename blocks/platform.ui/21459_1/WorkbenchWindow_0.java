		try {
			HandlerServiceImpl.push(windowContext, null);
			initializeDefaultServices();
		} finally {
			HandlerServiceImpl.pop();
		}
