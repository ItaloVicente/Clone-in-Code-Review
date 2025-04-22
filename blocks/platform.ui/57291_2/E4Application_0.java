		cssURI.filter(cssURIValue -> cssURIValue.startsWith("platform:/plugin/")).ifPresent(cssURIValue -> {
			System.err.println(
					"Warning. Use the \"platform:/plugin/Bundle-SymbolicName/path/filename.extension\" URI for the  parameter:   "
							+ IWorkbench.CSS_URI_ARG); // $NON-NLS-1$
			context.set(E4Application.THEME_ID, cssURIValue);
		});
