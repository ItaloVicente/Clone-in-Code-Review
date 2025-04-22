        Control contents = super.createContents(parent);

        initializeControls();
        validatePage();
        resourceGroup.setFocus();
        setTitle(IDEWorkbenchMessages.SaveAsDialog_title);
        ImageDescriptor descriptor = IDEInternalWorkbenchImages.getImageDescriptor(
                IDEInternalWorkbenchImages.IMG_DLGBAN_SAVEAS_DLG);
        if(descriptor != null) {
        	dlgTitleImage = descriptor.createImage();
        	setTitleImage(dlgTitleImage);
        }
        setMessage(IDEWorkbenchMessages.SaveAsDialog_message);

        return contents;
    }

    /**
     * The <code>SaveAsDialog</code> implementation of this <code>Window</code>
     * method disposes of the banner image when the dialog is closed.
     */
    @Override
