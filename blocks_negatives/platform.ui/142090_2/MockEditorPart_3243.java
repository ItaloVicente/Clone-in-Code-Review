                setSaveAsAllowed(saveAsToggle.getSelection());
            }
        });
        saveAsToggle.setSelection(saveAsAllowed);
    }
    /**
     * @see IEditorPart#doSave(IProgressMonitor)
     */
    @Override
