				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						if (fStyledText != null && !fStyledText.isDisposed()) {
							fStyledText.setMargins(fStyledText.getLeftMargin(), fStyledText.getTopMargin(),
									applyRightMargin, applyBottomMargin);
						}
