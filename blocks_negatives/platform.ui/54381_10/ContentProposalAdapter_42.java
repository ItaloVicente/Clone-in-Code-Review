			getControl().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (isValid()) {
						openProposalPopup(true);
					}
