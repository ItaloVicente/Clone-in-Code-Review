			tree.getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					if (!tree.isDisposed()) {
						tree.update();
						tree.getParent().layout();
					}
