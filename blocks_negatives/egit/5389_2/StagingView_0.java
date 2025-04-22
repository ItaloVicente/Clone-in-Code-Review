					menuMgr.add(new Action(UIText.StagingView_UnstageItemMenuLabel) {
						@Override
						public void run() {
							unstage((IStructuredSelection) tableViewer.getSelection());
						}
					});
