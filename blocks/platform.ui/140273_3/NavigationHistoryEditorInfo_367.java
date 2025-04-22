		if (memento == null) {
			IPersistableElement persistable = editorInput.getPersistable();
			memento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_EDITOR);
			memento.putString(IWorkbenchConstants.TAG_ID, editorID);
			memento.putString(IWorkbenchConstants.TAG_FACTORY_ID, persistable.getFactoryId());
			persistable.saveState(memento);
		}
		editorID = null;
		editorInput = null;
	}
