		super.doTearDown();
		getDecoratorManager().removeListener(this);
	}


	public void testEnableDecorator()  {
		getDecoratorManager().clearCaches();
		definition.setEnabled(true);
		getDecoratorManager().updateForEnablementChange();

	}

	public void testDisableDecorator() {
		getDecoratorManager().clearCaches();
		definition.setEnabled(false);
		getDecoratorManager().updateForEnablementChange();
	}

	@Override
