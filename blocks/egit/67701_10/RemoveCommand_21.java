				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {

					@Override
					public void run() {
						for (RepositoryNode node : selectedNodes) {
							node.clear();
						}
					}
				});
