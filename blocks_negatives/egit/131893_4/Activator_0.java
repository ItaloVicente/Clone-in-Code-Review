		final ServiceReference ssh;

		ssh = context.getServiceReference(IJSchService.class.getName());
		if (ssh != null) {
			SshSessionFactory.setInstance(new EclipseSshSessionFactory(
					(IJSchService) context.getService(ssh)));
