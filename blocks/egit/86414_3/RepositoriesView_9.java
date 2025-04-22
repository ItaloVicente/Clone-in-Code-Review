			return true;
		}
		Repository repository = AdapterUtils.adapt(context.getInput(),
				Repository.class);
		if (repository != null) {
			showRepository(repository);
			return true;
