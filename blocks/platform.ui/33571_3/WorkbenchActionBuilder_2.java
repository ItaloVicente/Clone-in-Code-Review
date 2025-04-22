				IAction buildHandler = new BuildAction(page.getWorkbenchWindow(),
						IncrementalProjectBuilder.INCREMENTAL_BUILD);
				((RetargetActionWithDefault) buildProjectAction).setDefaultHandler(buildHandler);
			}
		};
		getWindow().addPageListener(pageListener);

		prefListener = new Preferences.IPropertyChangeListener() {
			@Override
