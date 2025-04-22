		if (c == graph.getControl()) {

			c.addMenuDetectListener(new MenuDetectListener() {

				public void menuDetected(MenuDetectEvent e) {
					popupMgr.remove(new ActionContributionItem(compareAction));
					popupMgr.remove(new ActionContributionItem(
							compareVersionsAction));
					popupMgr.remove(new ActionContributionItem(
							viewVersionsAction));
					int size = ((IStructuredSelection) revObjectSelectionProvider
							.getSelection()).size();
					if (IFile.class.isAssignableFrom(getInput().getClass())) {
						popupMgr.add(new Separator());
						if (size == 1) {
							popupMgr.add(compareAction);
						} else if (size == 2) {
							popupMgr.add(compareVersionsAction);
						}
						if (size >= 1)
							popupMgr.add(viewVersionsAction);
