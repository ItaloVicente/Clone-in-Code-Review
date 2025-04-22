		if (parent.getDisplay() != null && parent.getDisplay().getSystemTaskBar() != null) {
			TaskItem taskItem = null;
			TaskBar systemTaskBar = parent.getDisplay().getSystemTaskBar();
			taskItem = systemTaskBar.getItem(parent.getShell());
			if (taskItem == null) {
				taskItem = systemTaskBar.getItem(null);
			}

			if (taskItem != null) {
				String taskBarProgressManagerKey = TaskBarProgressManager.class.getName() + ".instance"; //$NON-NLS-1$
				Object data = taskItem.getData(taskBarProgressManagerKey);
				if (data == null || !(data instanceof TaskBarProgressManager)) {
					taskItem.setData(taskBarProgressManagerKey, new TaskBarProgressManager(taskItem));
				}
			}
		}
