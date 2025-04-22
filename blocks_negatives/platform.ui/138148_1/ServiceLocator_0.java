		if (saveInContext) {
			IEclipseContext context = e4Context;
			if (context == null) {
				return;
			}
			context.set(api.getName(), service);
