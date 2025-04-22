
		Event[] events = new Event[urlsToOpen.size()];
		urlsToOpen.toArray(events);
		urlsToOpen.clear();

		for (Event event : events) {
			openUrl(display, event);
		}
	}

	private static void openUrl(Display display, Event event) {
		display.asyncExec(() -> {
			String uriScheme = getUriSchemeFromEvent(event.text);
			try {
				IUriSchemeProcessor.INSTANCE.handleUri(uriScheme, event.text);

			} catch (CoreException e) {
				String message = NLS.bind(IDEWorkbenchMessages.OpenDelayedUrlAction_cannotHandle, uriScheme);
				IDEWorkbenchPlugin.log(message, new Status(IStatus.ERROR, IDEApplication.PLUGIN_ID, message, e));
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window == null) {
					return;
				}
				MessageDialog.open(MessageDialog.ERROR, window.getShell(),
						IDEWorkbenchMessages.OpenDelayedUrlAction_title, message, SWT.SHEET);
			}
		});
	}

	private static String getUriSchemeFromEvent(String uriString) {
		try {
			URI uri = new URI(uriString);
			return uri.getScheme();

		} catch (URISyntaxException e) {
			String message = NLS.bind(IDEWorkbenchMessages.OpenDelayedUrlAction_invalidURL, uriString);
			IDEWorkbenchPlugin.log(message, new Status(IStatus.ERROR, IDEApplication.PLUGIN_ID, message, e));
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null) {
				return null;
			}
			MessageDialog.open(MessageDialog.ERROR, window.getShell(), IDEWorkbenchMessages.OpenDelayedUrlAction_title,
					message, SWT.SHEET);
			return null;
		}
