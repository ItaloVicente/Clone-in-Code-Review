
		Event[] events = new Event[urlsToOpen.size()];
		urlsToOpen.toArray(events);
		urlsToOpen.clear();

		for (Event event : events) {
			openUrl(display, event);
		}
	}

	private static void openUrl(Display display, Event event) {
		display.asyncExec(() -> {
			String uriScheme = getUriSchemeFromEvent(event);

			callUriSchemeHandler(event, uriScheme);
		});
	}

	private static String getUriSchemeFromEvent(Event event) {
		try {
			URI uri = new URI(event.text);
			return uri.getScheme();

		} catch (URISyntaxException e) {
			String message = NLS.bind(IDEWorkbenchMessages.OpenDelayedUrlAction_invalidURL, event.text);
			IDEWorkbenchPlugin.log(message, new Status(IStatus.ERROR, IDEApplication.PLUGIN_ID, message, e));
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null) {
				return null;
			}
			MessageDialog.open(MessageDialog.ERROR, window.getShell(), IDEWorkbenchMessages.OpenDelayedUrlAction_title,
					message, SWT.SHEET);
			return null;
		}
	}

	private static void callUriSchemeHandler(Event event, String uriScheme) {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXT_POINT_ID_URI_SCHEME_HANDLERS);
		for (IConfigurationElement element : elements) {
			if (uriScheme.equals(element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME))) {
				try {
					Object executableExtension = element.createExecutableExtension(EXT_POINT_ATTRIBUTE_CLASS);
					if (executableExtension instanceof Listener) {
						Listener listener = (Listener) executableExtension;
						listener.handleEvent(event);
						break;
					}
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
			}
		}
