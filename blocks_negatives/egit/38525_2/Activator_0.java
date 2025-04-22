		display.syncExec(new Runnable() {
			public void run() {
				ret.set(display.getActiveShell() != null);
			}
		});
		return ret.get();
