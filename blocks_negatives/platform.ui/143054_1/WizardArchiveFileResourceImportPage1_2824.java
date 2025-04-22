            overwriteExistingResourcesCheckbox.setSelection(settings
                    .getBoolean(STORE_OVERWRITE_EXISTING_RESOURCES_ID));
        }
    }

    /**
     * 	Since Finish was pressed, write widget values to the dialog store so that they
     *	will persist into the next invocation of this wizard page.
     *
     *	Note that this method is identical to the one that appears in the superclass.
     *	This is necessary because proper overriding of instance variables is not occurring.
     */
    @Override
