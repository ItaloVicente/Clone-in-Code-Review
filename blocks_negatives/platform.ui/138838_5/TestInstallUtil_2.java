		ServiceReference<PackageAdmin> packageAdminRef = context
				.getServiceReference(PackageAdmin.class);
        PackageAdmin packageAdmin = null;
        if (packageAdminRef != null) {
            packageAdmin = context.getService(packageAdminRef);
            if (packageAdmin == null) {
				return;
			}
        }
