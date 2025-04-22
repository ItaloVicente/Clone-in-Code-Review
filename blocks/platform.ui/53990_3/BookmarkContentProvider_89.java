            viewer.getControl().getDisplay().asyncExec(() -> {
			    Control ctrl = viewer.getControl();
			    if (ctrl == null || ctrl.isDisposed()) {
					return;
				}

			    viewer.refresh();
			});
