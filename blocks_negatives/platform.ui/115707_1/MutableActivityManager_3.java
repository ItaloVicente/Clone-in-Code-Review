			Object nv = event.getNewValue();
			boolean enabledWhen = nv == null ? false : ((Boolean) nv)
					.booleanValue();
			String id = event.getProperty();
			IActivity activity = (IActivity)activitiesById.get(id);
			if (activity.isEnabled() != enabledWhen) {
				if (enabledWhen) {
					addExpressionEnabledActivity(id);
				} else {
					removeExpressionEnabledActivity(id);
				}
