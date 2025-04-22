			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					receivedKeyDown = false;
					try {
						Thread.sleep(autoActivationDelay);
					} catch (InterruptedException e) {
					}
					if (!isValid() || receivedKeyDown) {
						return;
					}
					getControl().getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							openProposalPopup(true);
						}
					});
