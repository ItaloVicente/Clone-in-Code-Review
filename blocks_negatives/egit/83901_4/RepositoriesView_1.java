
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				selectReveal(new StructuredSelection(nodesToShow));
			}
		});
