			Repository repository = AdapterUtils.adapt(ssel.getFirstElement(),
					Repository.class);
			if (repository != null) {
				showRepository(repository);
				return;
			}
