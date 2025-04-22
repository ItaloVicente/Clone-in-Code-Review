		super.pageActivated(page);
		NavigationHistory nh = (NavigationHistory) page.getNavigationHistory();
		if (forward) {
			nh.setForwardAction(this);
		} else {
			nh.setBackwardAction(this);
		}
	}
