		if (o instanceof IAdaptable) {
			IContributorResourceAdapter adapted = Adapters.adapt(o,
					IContributorResourceAdapter.class);
			if (adapted instanceof IContributorResourceAdapter2) {
				IContributorResourceAdapter2 cra = (IContributorResourceAdapter2) adapted;
				return cra.getAdaptedResourceMapping((IAdaptable) o);
			}
