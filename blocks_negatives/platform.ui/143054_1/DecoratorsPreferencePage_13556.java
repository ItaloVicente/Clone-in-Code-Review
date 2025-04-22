                boolean checked = checkboxViewer.getChecked(definition);
                definition.setEnabled(checked);

            }
            manager.clearCaches();
            manager.updateForEnablementChange();
            return true;
        }
        return false;
    }

    /**
     * @see IWorkbenchPreferencePage#init(IWorkbench)
     */
    @Override
