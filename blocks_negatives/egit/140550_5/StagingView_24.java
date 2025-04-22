		syncExec(new Runnable() {
			@Override
			public void run() {
				setRedraw(false);
				try {
					refreshViewersInternal();
				} finally {
					setRedraw(true);
				}
