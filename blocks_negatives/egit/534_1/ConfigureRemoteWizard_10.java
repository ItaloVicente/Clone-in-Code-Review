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
