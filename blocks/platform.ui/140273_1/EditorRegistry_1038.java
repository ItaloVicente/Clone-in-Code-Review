			memento.save(writer);
			writer.close();
			store.setValue(IPreferenceConstants.EDITORS, writer.toString());
		} catch (IOException e) {
			MessageDialog.openError((Shell) null, "Error", "Unable to save resource associations."); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
	}

	public void setFileEditorMappings(FileEditorMapping[] newResourceTypes) {
		typeEditorMappings = new EditorMap();
		for (FileEditorMapping mapping : newResourceTypes) {
			typeEditorMappings.put(mappingKeyFor(mapping), mapping);
		}
		extensionImages = new HashMap<>();
		rebuildEditorMap();
		firePropertyChange(PROP_CONTENTS);
	}

	@Override
