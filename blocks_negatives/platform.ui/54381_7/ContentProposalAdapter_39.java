				control.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						recordCursorPosition();
						recomputeProposals(filterText);
					}
