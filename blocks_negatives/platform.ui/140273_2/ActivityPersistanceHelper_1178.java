	        WorkbenchPlugin.getDefault().savePluginPreferences();
        }
        finally {
            saving = false;
        }
    }

    /**
     * Save the enabled state of all activities.
     */
    public void shutdown() {
        unhookListeners();
        saveEnabledStates();
    }
