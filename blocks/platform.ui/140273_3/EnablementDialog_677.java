		Composite composite = (Composite) super.createDialogArea(parent);
		Font dialogFont = parent.getFont();
		composite.setFont(dialogFont);
		Label text = new Label(composite, SWT.NONE);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setFont(dialogFont);
		IActivityManager manager = PlatformUI.getWorkbench().getActivitySupport().getActivityManager();

		if (activityIds.size() == 1) {
			String activityId = activityIds.iterator().next();
			activitiesToEnable.add(activityId);
			selectedActivity = activityId;

			IActivity activity = manager.getActivity(activityId);
			String activityText;
			try {
				activityText = activity.getName();
			} catch (NotDefinedException e) {
				activityText = activity.getId();
			}
			text.setText(MessageFormat.format(RESOURCE_BUNDLE.getString("requiresSingle"), //$NON-NLS-1$
