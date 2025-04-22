		MenuManagerEventHelper.getInstance().setShowHelper(
				ContextInjectionFactory.make(MenuManagerShowProcessor.class,
						context));
		MenuManagerEventHelper.getInstance().setHideHelper(
				ContextInjectionFactory.make(MenuManagerHideProcessor.class,
						context));
