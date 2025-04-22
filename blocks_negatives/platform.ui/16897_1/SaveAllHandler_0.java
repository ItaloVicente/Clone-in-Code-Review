	boolean canExecute(@Optional MWindow window) {
		if (window != null) {
			IEclipseContext context = window.getContext();
			if (context != null) {
				EPartService partService = context.get(EPartService.class);
				if (partService != null) {
					return !partService.getDirtyParts().isEmpty();
				}
			}
