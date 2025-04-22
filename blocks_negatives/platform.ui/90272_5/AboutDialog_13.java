        button.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent event) {
                AboutBundleGroupData[] groupInfos = buttonManager
                        .getRelatedInfos(info);
                AboutBundleGroupData selection = (AboutBundleGroupData) event.widget
                        .getData();

                AboutFeaturesDialog d = new AboutFeaturesDialog(getShell(),
                        productName, groupInfos, selection);
                d.open();
            }
        });
