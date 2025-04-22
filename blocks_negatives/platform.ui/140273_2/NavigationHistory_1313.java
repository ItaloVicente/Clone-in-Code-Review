        /* Ignore all entries until the async exec runs. Workaround to avoid
         * extra entry when using Open Declaration (F3) that opens another editor. */
        ignoreEntries++;
        getDisplay().asyncExec(() -> {
		    if (--ignoreEntries == 0) {
		        if (part.getEditorSite() instanceof EditorSite) {
