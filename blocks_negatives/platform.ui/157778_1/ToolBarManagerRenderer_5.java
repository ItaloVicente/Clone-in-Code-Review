			ToolBarManager parent = null;
			if (ici instanceof MenuManager) {
				parent = (ToolBarManager) ((MenuManager) ici).getParent();
			} else if (ici instanceof ContributionItem) {
				parent = (ToolBarManager) ((ContributionItem) ici).getParent();
			}
