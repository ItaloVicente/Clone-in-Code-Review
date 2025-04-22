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
				ISaveablePart saveable1 = SaveableHelper.getSaveable(wrapped);
				if (saveable1 != null) {
					part.setDirty(saveable1.isDirty());
				} else if (part.isDirty()) {
					part.setDirty(false);
				}
				break;
			case IWorkbenchPartConstants.PROP_INPUT:
				WorkbenchPartReference ref = getReference();
				((WorkbenchPage) ref.getSite().getPage()).firePartInputChanged(ref);
				break;
