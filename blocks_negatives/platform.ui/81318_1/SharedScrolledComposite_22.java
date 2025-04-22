			getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					reflowPending = false;
					if (!isDisposed())
						reflow(flushCache);
				}
