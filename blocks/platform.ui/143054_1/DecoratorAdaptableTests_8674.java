		PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestAdaptableDecoratorContributor.ID, false);
		PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestUnadaptableDecoratorContributor.ID, false);
		PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceDecoratorContributor.ID, false);
		PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceMappingDecoratorContributor.ID, false);
		super.doTearDown();
	}

