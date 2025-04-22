		ILogListener ll = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				_statusCount++;
			}
		};
