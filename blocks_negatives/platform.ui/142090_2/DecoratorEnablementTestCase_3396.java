        super.doTearDown();
        getDecoratorManager().removeListener(this);
    }


    /**
     * Test enabling the contributor
     */
    public void testEnableDecorator()  {
        getDecoratorManager().clearCaches();
        definition.setEnabled(true);
        getDecoratorManager().updateForEnablementChange();

    }

    /**
     * Test disabling the contributor
     */
    public void testDisableDecorator() {
        getDecoratorManager().clearCaches();
        definition.setEnabled(false);
        getDecoratorManager().updateForEnablementChange();
    }

    /*
     * @see ILabelProviderListener#labelProviderChanged(LabelProviderChangedEvent)
     */
    @Override
