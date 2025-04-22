        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestAdaptableDecoratorContributor.ID, false);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestUnadaptableDecoratorContributor.ID, false);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceDecoratorContributor.ID, false);
        PlatformUI.getWorkbench().getDecoratorManager().setEnabled(TestResourceMappingDecoratorContributor.ID, false);
        super.doTearDown();
    }

    /**
     * This tests adaptable contributions that are not IResource.
     *
     * @since 3.1
     */
