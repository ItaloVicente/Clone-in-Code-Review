	private boolean showsHead(@NonNull Repository db) {
		return new RefFilterHelper(db).getRefFilters().stream()
				.anyMatch(f -> f.isSelected()
						&& Constants.HEAD.equals(f.getFilterString()));
	}

