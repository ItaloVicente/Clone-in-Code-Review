		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				if (realizedElements != null) {
					realizedElements.addAll(knownElements);
				}
