		return workbench.saveAll(window, window, new RepositorySaveableFilter(
				repository) {
			@Override
			public boolean select(Saveable saveable,
					IWorkbenchPart[] containingParts) {
				if (partToIgnore != null
						&& asList(containingParts).contains(partToIgnore)) {
					return false;
				}
				return super.select(saveable, containingParts);
			}
		}, true);
