        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestAdaptableDecoratorContributor.ID, true);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestUnadaptableDecoratorContributor.ID, true);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceDecoratorContributor.ID, true);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceMappingDecoratorContributor.ID, true);
        super.doSetUp();
    }

    @Override
