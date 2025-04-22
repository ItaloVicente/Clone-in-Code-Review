			control.getDisplay().timerExec(popupDelay, new Runnable() {
				@Override
				public void run() {
					toolTipShow(shell, event);
				}
			});
