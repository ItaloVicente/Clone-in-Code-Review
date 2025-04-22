                if (!(element instanceof IProject)) {
                    return false;
                }
                IProject project = (IProject) element;
				boolean isProjectNameMatchingPattern = project.getName().matches(filterRegexPattern);
				if (!project.isAccessible() || !isProjectNameMatchingPattern) {
                    return false;
                }
                projectHolder[0] = project;
                return BuildUtilities.isEnabled(projectHolder, IncrementalProjectBuilder.CLEAN_BUILD);
            }
        });
        projectNames.setInput(ResourcesPlugin.getWorkspace().getRoot());
        GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
        data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
        data.heightHint = IDialogConstants.ENTRY_FIELD_WIDTH;
        projectNames.getTable().setLayoutData(data);
        projectNames.setCheckedElements(selection);
        Object[] checked = projectNames.getCheckedElements();
