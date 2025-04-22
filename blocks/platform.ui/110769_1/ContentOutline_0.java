			try {
				page.createControl(getPageBook());
			} catch (Exception e) {
				String message = "Failed to create outline control for " + page.getClass(); //$NON-NLS-1$
				Platform.getLog(Platform.getBundle(VIEWS_PLUGIN_ID))
						.log(new Status(IStatus.ERROR, VIEWS_PLUGIN_ID, IStatus.OK, message, e));
				page.dispose();
				return null;
			}
