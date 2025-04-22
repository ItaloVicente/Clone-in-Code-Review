		if (o instanceof IAdaptable) {
			IContributorResourceAdapter adapted = AdapterUtils.adapt(o,
					IContributorResourceAdapter.class);
			if (adapted instanceof IContributorResourceAdapter2) {
				IContributorResourceAdapter2 cra = (IContributorResourceAdapter2) adapted;
				return cra.getAdaptedResourceMapping((IAdaptable) o);
			}
