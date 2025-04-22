
		RepositoryMapping mapping = RepositoryMapping.getMapping(res
				.getProject());
		if (mapping != null) {
			Repository repository = mapping.getRepository();
			return testRepositoryState(repository, property);
		}
		return false;
	}

	public static boolean testRepositoryState(Repository repository, String property) {
		if ("isShared".equals(property)) //$NON-NLS-1$
			return repository != null;
		if (repository != null) {
			RepositoryState state = repository.getRepositoryState();
			if (property.length() > 3 && property.startsWith("is")) { //$NON-NLS-1$
				String lookFor = property.substring(2,3) + property.substring(3).replaceAll("([A-Z])","_$1").toUpperCase();  //$NON-NLS-1$//$NON-NLS-2$
				if (state.toString().equals(lookFor))
					return true;
			}
			try {
				Method method = RepositoryState.class.getMethod(property);
				if (method.getReturnType() == boolean.class) {
					Boolean ret = (Boolean) method.invoke(state);
					return ret.booleanValue();
				}
			} catch (Exception e) {
			}
		}
