	public static final Comparator<Ref> REF_ASCENDING_COMPARATOR = new Comparator<Ref>() {
		@Override
		public int compare(Ref o1, Ref o2) {
			String name1 = o1.getName();
			String name2 = o2.getName();
			if (name1.startsWith(Constants.R_TAGS)
					&& name2.startsWith(Constants.R_TAGS)
					&& !isSortTagsAscending()) {
				name1 = name2;
				name2 = o1.getName();
			}
			return STRING_ASCENDING_COMPARATOR.compare(name1, name2);
		}
	};

	private static boolean isSortTagsAscending() {
		if (sortTagsAscending == null) {
			ICommandService srv = CommonUtils.getService(
					PlatformUI.getWorkbench(), ICommandService.class);
			State currentToggleState = srv
					.getCommand(TOGGLE_TAG_SORTING_COMMAND_ID)
					.getState(RegistryToggleState.STATE_ID);
			if (currentToggleState != null) {
				Object value = currentToggleState.getValue();
				if (value instanceof Boolean) {
					sortTagsAscending = ((Boolean) value);
				}
			}
			if (sortTagsAscending == null) {
				Activator.logError("Using default sort order", //$NON-NLS-1$
						new IllegalStateException(
								"Could not determine current tag sorting toggle state"));//$NON-NLS-1$
				sortTagsAscending = Boolean.FALSE;
			}
		}
		return sortTagsAscending.booleanValue();
	}

	public static void unsetTagSortingOrder() {
		sortTagsAscending = null;
	}

	public static final Comparator<String> TAG_STRING_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (isSortTagsAscending()) {
				return STRING_ASCENDING_COMPARATOR.compare(o1, o2);
			} else {
				return STRING_ASCENDING_COMPARATOR.compare(o2, o1);
			}
		}
	};
