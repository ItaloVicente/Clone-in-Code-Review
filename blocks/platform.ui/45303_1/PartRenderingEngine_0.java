			AbstractPartRenderer abstractPartRenderer = customRendererMap.get(customURI);
			if (abstractPartRenderer != null) {
				return abstractPartRenderer;
			}

			IEclipseContext owningContext = modelService.getContainingContext(uiElement);
			IContributionFactory contributionFactory = owningContext.get(IContributionFactory.class);
			Object customRenderer = contributionFactory.create(customURI, owningContext);
