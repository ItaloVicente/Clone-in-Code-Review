		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				if (toolkit != null) {
					getSite().getPage().closeEditor(FormEditor.this, save);
				}
