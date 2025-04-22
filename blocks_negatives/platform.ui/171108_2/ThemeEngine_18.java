		private EventAdmin getEventAdmin() {
			Bundle bundle = FrameworkUtil.getBundle(this.getClass());
			if (bundle == null) {
				return null;
			}
			BundleContext context = bundle.getBundleContext();
			ServiceReference<EventAdmin> eventAdminRef = context.getServiceReference(EventAdmin.class);
			return context.getService(eventAdminRef);
		}
