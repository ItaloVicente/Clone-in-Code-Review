	}

	protected abstract MenuManager getActionMenuManager(ListView view)
			throws Throwable;

	protected abstract void testAction(MenuManager mgr, String action,
			boolean expected) throws Throwable;
