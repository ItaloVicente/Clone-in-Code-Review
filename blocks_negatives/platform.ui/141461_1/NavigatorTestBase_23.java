		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof ActionContributionItem) {
				ActionContributionItem aci = (ActionContributionItem) items[i];
				if (aci.getAction().getText().startsWith(item))
					return true;
				if (DEBUG)
					System.out.println("action text: " + aci.getAction().getText());
			}
