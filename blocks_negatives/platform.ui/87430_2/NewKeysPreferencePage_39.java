			BusyIndicator.showWhile(fFilteredTree.getViewer().getTree().getDisplay(), new Runnable() {
				@Override
				public void run() {
					keyController.setDefaultBindings(fBindingService);
				}
			});
