		} else if ((style & IWorkbenchBrowserSupport.AS_VIEW) != 0)
			webBrowser = new InternalBrowserViewInstance(browserId, style,
					name, tooltip);
		else
			webBrowser = new InternalBrowserEditorInstance(browserId,
					style, name, tooltip);
