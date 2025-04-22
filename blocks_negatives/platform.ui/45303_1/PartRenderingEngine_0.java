			if (customRendererMap.get(customURI) instanceof AbstractPartRenderer)
				return customRendererMap.get(customURI);

			IEclipseContext owningContext = modelService
					.getContainingContext(uiElement);
			IContributionFactory contributionFactory = (IContributionFactory) owningContext
					.get(IContributionFactory.class.getName());
			Object customRenderer = contributionFactory.create(customURI,
					owningContext);
