		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				resourceManager = new LocalResourceManager(
						JFaceResources.getResources());
			}

		});
