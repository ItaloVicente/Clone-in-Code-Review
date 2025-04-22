					menuMgr.add(new Action(UIText.StagingView_StageItemMenuLabel) {
						@Override
						public void run() {
							stage((IStructuredSelection) tableViewer.getSelection());
						}
					});
