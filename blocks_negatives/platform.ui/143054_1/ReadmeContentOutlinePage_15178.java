            MessageDialog.openInformation(shell, MessageUtil
                    .getString("Readme_Outline"), //$NON-NLS-1$
        }
    }

    /**
     * Creates a new ReadmeContentOutlinePage.
     */
    public ReadmeContentOutlinePage(IFile input) {
        super();
        this.input = input;
    }

    /**
     * Creates the control and registers the popup menu for this page
     * Menu id "org.eclipse.ui.examples.readmetool.outline"
     */
    @Override
