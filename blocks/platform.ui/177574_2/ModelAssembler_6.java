			logger.warn("Could not run processor: {0}", e); //$NON-NLS-1$
		}
	}

	private void runProcessor(IModelProcessorContribution processor) {
		IEclipseContext localContext = EclipseContextFactory.create();

		for (ModelElement element : processor.getModelElements()) {
			String id = element.getId();

			if (id == null) {
				logger.warn("No element id given"); //$NON-NLS-1$
				continue;
			}

			String key = element.getContextKey();
			if (key == null) {
				key = id;
			}

			MApplicationElement el = ModelUtils.findElementById(application, id);
			if (el == null) {
				logger.warn("Could not find element with id '{0}'", id); //$NON-NLS-1$
			}
			localContext.set(key, el);
		}

		try {
			Object o = null;
			if (processor.getProcessorClass() != null) {
				o = ContextInjectionFactory.make(processor.getProcessorClass(), localContext);
			} else {
				o = processor;
			}
			if (o == null) {
				logger.warn("Unable to create processor {0} from {1}", //$NON-NLS-1$
						processor.getProcessorClass().getName(),
						FrameworkUtil.getBundle(processor.getProcessorClass()).getSymbolicName());
			} else {
				ContextInjectionFactory.invoke(o, Execute.class, context, localContext, null);
			}
		} catch (Exception e) {
			logger.warn("Could not run processor: {0}", e); //$NON-NLS-1$
