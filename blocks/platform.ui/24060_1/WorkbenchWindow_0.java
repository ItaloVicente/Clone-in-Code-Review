
		ModelAssembler contribProcessor = ContextInjectionFactory.make(ModelAssembler.class,
				model.getContext());
		contribProcessor.processModel();
		model.getMainMenu().setToBeRendered(false);
		model.getMainMenu().setToBeRendered(true);
