	private void createMenuContributionWithImperativeExpression(MApplication application) {
		MMenuContribution mmc = ems.createModelElement(MMenuContribution.class);
		mmc.setElementId("test.contrib2");
		mmc.setParentId("org.eclipse.ui.main.menu");
		mmc.setPositionInParent("after=additions");

		MMenu menu = ems.createModelElement(MMenu.class);
		menu.setElementId("vanish");
		menu.setLabel("Vanish");
		mmc.getChildren().add(menu);

		MImperativeExpression exp = ems.createModelElement(MImperativeExpression.class);
		exp.setTracking(true);
		exp.setContributionURI(
				"bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.ImperativeExpressionTestEvaluation");
		mmc.setVisibleWhen(exp);
		menu.setVisibleWhen(exp);

		application.getMenuContributions().add(mmc);

		mmc = ems.createModelElement(MMenuContribution.class);
		mmc.setElementId("test.contrib3");
		mmc.setParentId("vanish");
		mmc.setPositionInParent("after=additions");

		MMenuItem item1 = ems.createModelElement(MDirectMenuItem.class);
		item1.setElementId("mmc.item2");
		item1.setLabel("mmc.item2");
		mmc.getChildren().add(item1);

		application.getMenuContributions().add(mmc);
	}

