		switch (hsClassName) {
		case "org.eclipse.ui.internal.handlers.HandlerService":
			{
				Field hpField = hs.getClass().getDeclaredField("handlerPersistence");
				hpField.setAccessible(true);
				HandlerPersistence hp = (HandlerPersistence) hpField.get(hs);
				Field activationsField = hp.getClass().getDeclaredField("handlerActivations");
				activationsField.setAccessible(true);
				activations = (Collection<IHandlerActivation>) activationsField.get(hp);
				assertNotNull(activations);
				break;
			}
		case "org.eclipse.ui.internal.handlers.LegacyHandlerService":
			{
				Field hsField = hs.getClass().getDeclaredField("LEGACY_H_ID");
				String LEGACY_H_ID = (String) hsField.get(null);
				Field hpField = hs.getClass().getDeclaredField("eclipseContext");
				hpField.setAccessible(true);
				Object eclipseContext = hpField.get(hs);
				assertNotNull(eclipseContext);
				Method getMethod = eclipseContext.getClass().getDeclaredMethod("get", String.class);
				activations = (Collection<IHandlerActivation>) getMethod.invoke(eclipseContext,
						LEGACY_H_ID + CHECK_HANDLER_ID);
				assertNotNull(activations);
				break;
			}
		default:
