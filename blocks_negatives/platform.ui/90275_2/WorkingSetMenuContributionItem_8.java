        mi.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                IWorkingSetManager manager = PlatformUI.getWorkbench()
                        .getWorkingSetManager();
                actionGroup.setWorkingSet(workingSet);
                manager.addRecentWorkingSet(workingSet);
            }
        });
