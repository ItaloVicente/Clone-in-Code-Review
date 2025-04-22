		popupMgr.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager manager) {
				popupMgr.remove(new ActionContributionItem(compareAction));
				popupMgr.remove(new ActionContributionItem(compareVersionsAction));
				popupMgr.remove(new ActionContributionItem(viewVersionsAction));
				int size = ((IStructuredSelection) revObjectSelectionProvider
						.getSelection()).size();
				if (IFile.class.isAssignableFrom(getInput()
						.getClass())) {
					if (size == 1 ) {
						popupMgr.add(compareAction);
					}
					else if (size == 2) {
						popupMgr.add(compareVersionsAction);
