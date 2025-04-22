
		this.application.getMenuContributions().stream()
				.filter(menuModel -> menuModel.getPositionInParent().startsWith("popup:toolbar")) //$NON-NLS-1$
				.forEach(menuContribution -> {
			menuContribution.getChildren().forEach(itemModel -> {
				if (itemModel instanceof MItem) {
							final IEclipseContext lclContext = getContext(itemModel);
							HandledContributionItem itemRenderer = ContextInjectionFactory
									.make(HandledContributionItem.class, lclContext);
					itemRenderer.setModel((MItem) itemModel);
					itemRenderer.fill(toolControlMenu, 0);
				}
			});
		});
