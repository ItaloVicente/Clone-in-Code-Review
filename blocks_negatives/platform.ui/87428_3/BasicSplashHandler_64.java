			updateUI(new Runnable() {

				@Override
				public void run() {
					if (isDisposed())
						return;
					AbsolutePositionProgressMonitorPart.super
							.internalWorked(work);
				}
