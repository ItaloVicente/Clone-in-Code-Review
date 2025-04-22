            memento.save(writer);
            writer.close();
            store.setValue(IPreferenceConstants.EDITORS, writer.toString());
        } catch (IOException e) {
            MessageDialog.openError((Shell) null,
                    "Error", "Unable to save resource associations."); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }
    }

    /**
     * Set the collection of FileEditorMappings. The given collection is
     * converted into the internal hash table for faster lookup Each mapping
     * goes from an extension to the collection of editors that work on it. This
     * operation will rebuild the internal editor mappings.
     *
     * @param newResourceTypes
     *            te new file editor mappings.
     */
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
