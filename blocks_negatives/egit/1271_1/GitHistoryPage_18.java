					popupMgr.remove(new ActionContributionItem(createPatchAction));
					popupMgr.remove(new ActionContributionItem(compareAction));
					popupMgr.remove(new ActionContributionItem(
							compareVersionsAction));
					popupMgr.remove(new ActionContributionItem(
							viewVersionsAction));
					int size = ((IStructuredSelection) revObjectSelectionProvider
							.getSelection()).size();
					if (size == 1) {
