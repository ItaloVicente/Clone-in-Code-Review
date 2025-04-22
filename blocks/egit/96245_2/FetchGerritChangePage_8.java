	private void preFetch(ChangeList list) {
		try {
			list.fetch();
		} catch (InvocationTargetException e) {
			Activator.handleError(e.getLocalizedMessage(), e.getCause(), true);
		}
	}

