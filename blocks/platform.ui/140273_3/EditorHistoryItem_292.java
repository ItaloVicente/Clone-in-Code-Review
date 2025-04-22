		IPersistableElement persistable = input.getPersistable();
		String inputId = persistable == null ? null : persistable.getFactoryId();
		String myId = getFactoryId();
		return myId == null ? inputId == null : myId.equals(inputId);
	}

	public String getFactoryId() {
		if (isRestored()) {
			if (input != null) {
				IPersistableElement persistable = input.getPersistable();
				if (persistable != null) {
					return persistable.getFactoryId();
				}
			}
		} else if (memento != null) {
			return memento.getString(IWorkbenchConstants.TAG_FACTORY_ID);
		}
		return null;
	}

	public IStatus restoreState() {
		Assert.isTrue(!isRestored());

		IStatus result = Status.OK_STATUS;
		IMemento memento = this.memento;
		this.memento = null;

		String factoryId = memento.getString(IWorkbenchConstants.TAG_FACTORY_ID);
		if (factoryId == null) {
			WorkbenchPlugin.log("Unable to restore mru list - no input factory ID.");//$NON-NLS-1$
			return result;
		}
		IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(factoryId);
		if (factory == null) {
			return result;
		}
		IMemento persistableMemento = memento.getChild(IWorkbenchConstants.TAG_PERSISTABLE);
		if (persistableMemento == null) {
			WorkbenchPlugin.log("Unable to restore mru list - no input element state: " + factoryId);//$NON-NLS-1$
			return result;
		}
		IAdaptable adaptable = factory.createElement(persistableMemento);
		if (adaptable == null || (adaptable instanceof IEditorInput) == false) {
			return result;
		}
		input = (IEditorInput) adaptable;
		String editorId = memento.getString(IWorkbenchConstants.TAG_ID);
		if (editorId != null) {
			IEditorRegistry registry = WorkbenchPlugin.getDefault().getEditorRegistry();
			descriptor = registry.findEditor(editorId);
		}
		return result;
	}

	public boolean canSave() {
		return !isRestored() || (getInput() != null && getInput().getPersistable() != null);
	}

	public IStatus saveState(IMemento memento) {
		if (!isRestored()) {
			memento.putMemento(this.memento);
		} else if (input != null) {

			IPersistableElement persistable = input.getPersistable();
			if (persistable != null) {
				IMemento persistableMemento = memento.createChild(IWorkbenchConstants.TAG_PERSISTABLE);
				persistable.saveState(persistableMemento);
				memento.putString(IWorkbenchConstants.TAG_FACTORY_ID, persistable.getFactoryId());
				if (descriptor != null && descriptor.getId() != null) {
					memento.putString(IWorkbenchConstants.TAG_ID, descriptor.getId());
				}
				memento.putString(IWorkbenchConstants.TAG_NAME, input.getName());
				memento.putString(IWorkbenchConstants.TAG_TOOLTIP, input.getToolTipText());
			}
		}
		return Status.OK_STATUS;
	}
