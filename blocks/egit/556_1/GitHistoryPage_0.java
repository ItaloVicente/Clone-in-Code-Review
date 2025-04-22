		if (c == graph.getControl()) {

			c.addMenuDetectListener(new MenuDetectListener() {

				public void menuDetected(MenuDetectEvent e) {
					popupMgr.remove(new ActionContributionItem(compareAction));
					popupMgr.remove(new ActionContributionItem(
							compareVersionsAction));
					int size = ((IStructuredSelection) revObjectSelectionProvider
							.getSelection()).size();
					if (IFile.class.isAssignableFrom(getInput().getClass())) {
						if (size == 1 ) {
							popupMgr.add(compareAction);
						} else if (size == 2) {
							popupMgr.add(compareVersionsAction);
						}
