			IEclipseContext context = dirtyPart.getContext();
			if (context == null) {
				log("Failed to persist contents of part", //$NON-NLS-1$
						"Failed to persist contents of part ({0}) because Part#getContext() returned null", //$NON-NLS-1$
						dirtyPart.getElementId(), new RuntimeException());
				return false;
			}
			ContextInjectionFactory.invoke(client, Persist.class, context);
