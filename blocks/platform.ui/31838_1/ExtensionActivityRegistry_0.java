		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		for (Iterator i = activityDefinitions.iterator(); i.hasNext();) {
			ActivityDefinition activityDef = (ActivityDefinition) i.next();
			String id = activityDef.getId();
			String preferenceKey = createPreferenceKey(id);
			if ("".equals(store.getDefaultString(preferenceKey))) //$NON-NLS-1$
				continue;
			if (store.getDefaultBoolean(preferenceKey)) {
				if (!defaultEnabledActivities.contains(id) && activityDef.getEnabledWhen() == null)
					defaultEnabledActivities.add(id);
			} else {
				defaultEnabledActivities.remove(id);
			}
		}

