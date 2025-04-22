		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
						.update(DECORATOR_ID);
			}
		});
