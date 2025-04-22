		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				result[0] = getEditorSite().getPage().closeEditor(WebBrowserEditor.this, false);
			}
		});
