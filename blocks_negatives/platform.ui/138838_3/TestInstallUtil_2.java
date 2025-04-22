    public static void refreshPackages(Bundle[] bundles) {
		ServiceReference<PackageAdmin> packageAdminRef = context
				.getServiceReference(PackageAdmin.class);
        PackageAdmin packageAdmin = null;
        if (packageAdminRef != null) {
            packageAdmin = context.getService(packageAdminRef);
            if (packageAdmin == null) {
				return;
			}
        }

        final boolean[] flag = new boolean[] { false };
		FrameworkListener listener = event -> {
			if (event.getType() == FrameworkEvent.PACKAGES_REFRESHED) {
				synchronized (flag) {
					flag[0] = true;
					flag.notifyAll();
				}
			}
		};
        context.addFrameworkListener(listener);
        packageAdmin.refreshPackages(bundles);
        synchronized (flag) {
            while (!flag[0]) {
                try {
                    flag.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        context.removeFrameworkListener(listener);
        context.ungetService(packageAdminRef);
    }
