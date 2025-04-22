	    for (IContributionItem item1 : items) {
		if (item1 instanceof MenuManager) {
		    MenuManager childMm = (MenuManager) item1;
		    if (DEBUG) {
			System.out.println("menu text: " + childMm.getMenuText());
		    }
		    if (childMm.getMenuText().indexOf(item) >= 0)
			return childMm;
		} else if (item1 instanceof ActionContributionItem) {
		    ActionContributionItem aci = (ActionContributionItem) item1;
		    if (DEBUG) {
			System.out.println("action text: " + aci.getAction().getText());
		    }
		    if (aci.getAction().getText().indexOf(item) >= 0)
			return aci;
