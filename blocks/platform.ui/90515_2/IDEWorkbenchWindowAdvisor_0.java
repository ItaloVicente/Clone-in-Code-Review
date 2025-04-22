			for (Object element : wbAdvisor.getNewlyAddedBundleGroups().entrySet()) {
Map.Entry entry = (Map.Entry) element;
AboutInfo info = (AboutInfo) entry.getValue();

if (info != null && info.getWelcomePageURL() != null) {
			welcomeFeatures.add(info);
			String pi = info.getBrandingBundleId();
			if (pi != null) {
				Bundle bundle = Platform.getBundle(pi);
				if (bundle != null) {
					try {
						bundle.start(Bundle.START_TRANSIENT);
					} catch (BundleException exception) {
						StatusManager
								.getManager()
								.handle(
										new Status(
												IStatus.ERROR,
												IDEApplication.PLUGIN_ID,
												"Failed to load feature", exception));//$NON-NLS-1$
