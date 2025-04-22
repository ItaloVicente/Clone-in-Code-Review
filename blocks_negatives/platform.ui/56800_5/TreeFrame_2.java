        if (!(input instanceof IAdaptable)) {
			return;
		}

        IPersistableElement persistable = (IPersistableElement) ((IAdaptable) input)
                .getAdapter(IPersistableElement.class);
