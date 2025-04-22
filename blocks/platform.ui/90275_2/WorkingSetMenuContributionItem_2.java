        mi.addSelectionListener(widgetSelectedAdapter(e -> {
		    IWorkingSetManager manager = PlatformUI.getWorkbench()
		            .getWorkingSetManager();
		    actionGroup.setWorkingSet(workingSet);
		    manager.addRecentWorkingSet(workingSet);
		}));
