	    for (IContributionItem item1 : items) {
		if (item1 instanceof ActionContributionItem) {
		    ActionContributionItem aci = (ActionContributionItem) item1;
		    if (aci.getAction().getText().startsWith(item))
			return true;
		    if (DEBUG)
			System.out.println("action text: " + aci.getAction().getText());
