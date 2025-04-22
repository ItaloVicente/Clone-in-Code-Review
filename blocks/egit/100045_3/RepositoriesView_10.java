			Repository repository = AdapterUtils.adapt(ssel.getFirstElement(),
					Repository.class);
			if (repository != null) {
				show(new ShowInContext(repository, null));
				return;
			}
