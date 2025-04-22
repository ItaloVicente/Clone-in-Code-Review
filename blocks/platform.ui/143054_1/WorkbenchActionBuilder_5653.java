				menu.add(ContributionItemFactory.VIEWS_SHOW_IN
						.create(window));
			}
		};
		register(showInQuickMenu);

		final String newQuickMenuId = "org.eclipse.ui.file.newQuickMenu"; //$NON-NLS-1$
		newQuickMenu = new QuickMenuAction(newQuickMenuId) {
			@Override
