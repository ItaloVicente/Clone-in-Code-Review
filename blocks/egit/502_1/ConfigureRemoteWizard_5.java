	@Override
	public IWizardPage getNextPage(IWizardPage page) {


		if (!createMode) {
			return super.getNextPage(page);
		}
		if (page instanceof SelectRemoteNamePage) {
			SelectRemoteNamePage srp = (SelectRemoteNamePage) page;
			if (srp.configureFetch.getSelection()) {
				return getPages()[1];
			}
			if (srp.configurePush.getSelection()) {
				return getPages()[3];
			}
		}
		if (page == getPages()[1] || page == getPages()[3]) {
			RefSpecPage next = (RefSpecPage) getPages()[2];
			next
					.setConfigName(((SelectRemoteNamePage) getPages()[0]).remoteName
							.getText());
			next = (RefSpecPage) getPages()[4];
			next
					.setConfigName(((SelectRemoteNamePage) getPages()[0]).remoteName
							.getText());

		}
		if (page == getPages()[2]) {
			SelectRemoteNamePage srp = (SelectRemoteNamePage) getPages()[0];
			if (srp.configurePush.getSelection()) {
				return getPages()[3];
			} else {
				return null;
			}
		}

		return super.getNextPage(page);
	}

	@Override
	public boolean canFinish() {
		if (createMode) {
			IWizardPage[] pages = getPages();
			if (pages[0].isPageComplete()) {
				boolean done = true;
				SelectRemoteNamePage srp = (SelectRemoteNamePage) pages[0];
				if (srp.configureFetch.getSelection())
					done = done & pages[1].isPageComplete()
							& pages[2].isPageComplete();
				if (srp.configurePush.getSelection())
					done = done & pages[3].isPageComplete()
							& pages[4].isPageComplete();
				return done;
			}
			return false;
		}
		return super.canFinish();
	}

