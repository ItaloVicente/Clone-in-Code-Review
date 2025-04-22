
		for (MMenuContribution menuModel : application.getMenuContributions()) {
			String positionInParent = menuModel.getPositionInParent();
			if (positionInParent.startsWith("popup:toolbar")) { //$NON-NLS-1$
				for (MMenuElement itemModel : menuModel.getChildren()) {
					if (itemModel instanceof MItem) {
						final IEclipseContext lclContext = getContext(itemModel);
						HandledContributionItem itemRenderer = ContextInjectionFactory
								.make(HandledContributionItem.class, lclContext);
						itemRenderer.setModel((MItem) itemModel);
						itemRenderer.fill(toolControlMenu, 0);
					}
				}
			}
		}
