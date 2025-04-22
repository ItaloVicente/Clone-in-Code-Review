		wrapped.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propId) {
				switch (propId) {
				case IWorkbenchPartConstants.PROP_TITLE:
					part.setLabel(computeLabel());

					if (wrapped.getTitleImage() != null) {
						Image newImage = wrapped.getTitleImage();
						part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
								newImage);
					}
					String titleToolTip = wrapped.getTitleToolTip();
					if (titleToolTip != null) {
						part.getTransientData().put(IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY, titleToolTip);
					}
					break;
				case IWorkbenchPartConstants.PROP_DIRTY:
					boolean supportsDirtyState = SaveableHelper.isDirtyStateSupported(wrapped);
					if (!supportsDirtyState) {
						part.setDirty(false);
						return;
					}
					ISaveablePart saveable = SaveableHelper.getSaveable(wrapped);
					if (saveable != null) {
						part.setDirty(saveable.isDirty());
					} else if (part.isDirty()) {
						part.setDirty(false);
					}
					break;
				case IWorkbenchPartConstants.PROP_INPUT:
					WorkbenchPartReference ref = getReference();
					((WorkbenchPage) ref.getSite().getPage()).firePartInputChanged(ref);
					break;
