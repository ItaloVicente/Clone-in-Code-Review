	private boolean missingAboutToShowMethod(Object contribution) {
		boolean result = true;
		for (Method method : contribution.getClass().getMethods()) {
			if (method.isAnnotationPresent(AboutToShow.class)) {
				result = false;
			}
		}
		return result;
	}

