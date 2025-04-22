			String uriText = config.getURIs().get(0).toString();
			AsynchronousBranchList list = refs.get(uriText);
			if (list == null) {
				list = new AsynchronousBranchList(repository, uriText, null);
				refs.put(uriText, list);
				preFetch(list);
			}
		}
	}

	private void preFetch(AsynchronousBranchList list) {
		try {
			list.start();
		} catch (InvocationTargetException e) {
			Activator.handleError(e.getLocalizedMessage(), e.getCause(), true);
