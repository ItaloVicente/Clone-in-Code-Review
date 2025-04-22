        container = wizardContainer;
    }

    /**
     * Sets the default page image descriptor for this wizard.
     * <p>
     * This image descriptor will be used to generate an image for a page with
     * no image of its own; the image will be computed once and cached.
     * </p>
     * 
     * @param imageDescriptor
     *            the default page image descriptor
     */
    public void setDefaultPageImageDescriptor(ImageDescriptor imageDescriptor) {
        defaultImageDescriptor = imageDescriptor;
    }

    /**
     * Sets the dialog settings for this wizard.
     * <p>
     * The dialog settings is used to record state between wizard invocations
     * (for example, radio button selection, last import directory, etc.)
     * </p>
     * 
     * @param settings
     *            the dialog settings, or <code>null</code> if none
     * @see #getDialogSettings
     *  
     */
    public void setDialogSettings(IDialogSettings settings) {
        dialogSettings = settings;
    }

    /**
     * Controls whether the wizard needs Previous and Next buttons even if it
     * currently contains only one page.
     * <p>
     * This flag should be set on wizards where the first wizard page adds
     * follow-on wizard pages based on user input.
     * </p>
     * 
     * @param b
     *            <code>true</code> to always show Next and Previous buttons,
     *            and <code>false</code> to suppress Next and Previous buttons
     *            for single page wizards
     */
    public void setForcePreviousAndNextButtons(boolean b) {
        forcePreviousAndNextButtons = b;
    }

    /**
