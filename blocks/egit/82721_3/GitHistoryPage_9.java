		Set<IGlobalActionProvider> actionProviders = new HashSet<>();
		actionProviders.add(graph);
		actionProviders.add(commentViewer);
		actionProviders.add(fileViewer);
		globalActionHandler = new GlobalActionHandler(getSite().getActionBars(),
				actionProviders);
