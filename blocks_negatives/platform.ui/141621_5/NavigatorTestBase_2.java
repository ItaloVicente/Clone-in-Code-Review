		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof MenuManager) {
				MenuManager childMm = (MenuManager) items[i];
				if (DEBUG) {
					System.out.println("menu text: " + childMm.getMenuText());
				}
				if (childMm.getMenuText().indexOf(item) >= 0)
					return childMm;
			} else if (items[i] instanceof ActionContributionItem) {
				ActionContributionItem aci = (ActionContributionItem) items[i];
				if (DEBUG) {
					System.out.println("action text: " + aci.getAction().getText());
				}
				if (aci.getAction().getText().indexOf(item) >= 0)
					return aci;
			}
