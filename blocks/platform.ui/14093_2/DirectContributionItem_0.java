		} else {
			final IEclipseContext lclContext = getContext(model);
			IPresentationEngine engine = lclContext
					.get(IPresentationEngine.class);
			obj = engine.createGui(mmenu, toolItem.getParent(), lclContext);
			if (obj instanceof Menu) {
				return (Menu) obj;
			}
			if (logger != null) {
				logger.debug("Rendering returned " + obj); //$NON-NLS-1$
			}
