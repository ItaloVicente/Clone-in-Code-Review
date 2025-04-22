				toolbarManager.update(true);
				if (!tbCtrl.isDisposed()) {
					Control packParent = getPackParent(tbCtrl);
					packParent.pack();

					if (packParent.getParent() instanceof CTabFolder)
						packParent.getParent().layout(true);
				}
