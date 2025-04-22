		BusyIndicator.showWhile(control.getDisplay(), () -> {
			try {
				control.setRedraw(false);
				introPart.standbyStateChanged(standby);
			} finally {
				control.setRedraw(true);
			}

			setBarVisibility(standby);
