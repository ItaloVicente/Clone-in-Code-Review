				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						display.timerExec(-1, handler);
						display.removeFilter(SWT.KeyUp, idleListener);
						display.removeFilter(SWT.MouseUp, idleListener);
					}
