		if (getContainer().getCurrentPage() == mainPage) {
			if (mainPage.canFinishEarly()) {
				return true;
			}
		}
		return super.canFinish();
	}
