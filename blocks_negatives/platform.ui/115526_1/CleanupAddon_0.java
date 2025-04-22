		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				int tbrCount = modelService.toBeRenderedCount(container);

				boolean lastStack = isLastEditorStack(container);
				if (tbrCount == 0 && !lastStack) {
