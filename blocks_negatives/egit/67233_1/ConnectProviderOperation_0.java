		RepositoryMapping actualMapping = findActualRepository(repos,
				entry.getValue());
		if (actualMapping == null) {
			ms.add(Activator.error(NLS.bind(
					CoreText.ConnectProviderOperation_UnexpectedRepositoryError,
					new Object[] { project.getName(),
							entry.getValue().toString(), repos.toString() }),
					null));
			return;
		}
