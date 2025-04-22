			menu.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (originalChild == null) {
						popupContext.deactivate();
					} else {
						originalChild.activate();
					}
				}
			});
