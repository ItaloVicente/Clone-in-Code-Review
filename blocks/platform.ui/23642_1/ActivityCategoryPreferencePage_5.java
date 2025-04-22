			String name = null;
			ICategory category = (ICategory) element;
			try {
				name = category.getName();
			} catch (NotDefinedException e) {
				name = category.getId();
			}
			if (decorate && isLocked(category)) {
				name = NLS.bind(ActivityMessages.ActivitiesPreferencePage_lockedMessage, name);
			}
			return name;
		}

		public String getColumnText(Object element, int columnIndex) {
			return getText(element);
		}

		@Override
		public void dispose() {
			super.dispose();
			manager.dispose();
		}

		public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
			if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
				updateCategoryCheckState();
				fireLabelProviderChanged(new LabelProviderChangedEvent(this));
			}
		}
	}

	private class CategoryContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return WorkbenchActivityHelper.resolveCategories(workingCopy, (Set<?>) inputElement);
		}

		@Override
