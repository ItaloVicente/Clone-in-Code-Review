	/**
	 * Restore the state of the receiver from the dialog settings.
	 *
	 * @param dialogSettings
	 * @param view
	 */
	public void restoreState(IDialogSettings dialogSettings, ProblemView view) {
		if (dialogSettings == null) {
			selectDefaultGrouping(view);
			return;
		}

		IDialogSettings settings = dialogSettings
				.getSection(TableComparator.TAG_DIALOG_SECTION);
		if (settings == null) {
			selectDefaultGrouping(view);
			return;
		}

		String description = settings.get(TAG_FIELD);
		view.selectCategory(description, this);
	}

	/**
	 * Select the default grouping in the problem view
	 * @param view
	 */
	private void selectDefaultGrouping(ProblemView view) {
		view.selectCategoryField(MarkerSupportRegistry.getInstance()
				.getDefaultGroupField(), this);
	}
