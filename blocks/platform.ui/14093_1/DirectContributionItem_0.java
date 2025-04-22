		} else {
			final IEclipseContext lclContext = getContext(mmenu);
			IPresentationEngine engine = lclContext
					.get(IPresentationEngine.class);
			obj = engine.createGui(mmenu, toolItem.getParent(), lclContext);
			if (obj instanceof Menu) {
				return (Menu) obj;
			}
			System.err.println("Rendering returned " + obj); //$NON-NLS-1$
