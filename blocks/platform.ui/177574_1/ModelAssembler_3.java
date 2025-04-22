				ContextInjectionFactory.invoke(o, Execute.class, context, localContext, null);
			}
		} catch (Exception e) {
			logger.warn(e, "Could not run processor"); //$NON-NLS-1$
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
				logger.warn("Could not find element with id '" + id + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			localContext.set(key, el);
		}

		try {
			Object o = ContextInjectionFactory.make(processor.getProcessorClass(), localContext);
			if (o == null) {
				logger.warn("Unable to create processor " + processor.getProcessorClass().getName() + " from " //$NON-NLS-1$ //$NON-NLS-2$
						+ FrameworkUtil.getBundle(processor.getProcessorClass()).getSymbolicName());
			} else {
				ContextInjectionFactory.invoke(o, Execute.class, context, localContext, null);
