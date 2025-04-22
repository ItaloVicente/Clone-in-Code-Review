		if (element instanceof String) {
			return getActivityText(activityManager.getActivity((String) element));
		} else if (element instanceof IActivity) {
			return getActivityText((IActivity) element);
		} else {
			throw new IllegalArgumentException();
		}
	}
