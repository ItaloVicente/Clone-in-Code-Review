        getDisplay().asyncExec(new Runnable() {
            @Override
			public void run() {
                if (--ignoreEntries == 0) {
	                if (part.getEditorSite() instanceof EditorSite) {
						EditorSite site = (EditorSite) part.getEditorSite();
						Control c = (Control) site.getModel().getWidget();
		                if (c == null || c.isDisposed()) {
							return;
						}
		                NavigationHistoryEntry e = getEntry(activeEntry);
		                if (e != null
		                        && part.getEditorInput() != e.editorInfo.editorInput) {
							updateEntry(e);
						}
		                addEntry(part);
	                }
                }
            }
        });
