			String uriText = config.getURIs().get(0).toString();
			FutureRefs list = refs.get(uriText);
			if (list == null) {
				list = new FutureRefs(repository, uriText);
				refs.put(uriText, list);
				preFetch(list);
			}
		}
	}

	private void preFetch(FutureRefs list) {
		try {
			list.start();
		} catch (InvocationTargetException e) {
			Activator.handleError(e.getLocalizedMessage(), e.getCause(), true);
