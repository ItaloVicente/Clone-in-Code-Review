        noDefaultAndApplyButton();
        Composite panel = createComposite(parent, 2);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IReadmeConstants.PROPERTY_PAGE2_CONTEXT);

        int page = getPageIndex();
        switch (page) {
        case 1:
            createPageOne(panel);
            break;
        case 2:
            createPageTwo(panel);
            break;
        default:
        }
        return new Canvas(panel, 0);
    }

    /**
     * Utility method that creates a new label and sets up
     * its layout data.
     *
     * @param parent  the parent of the label
     * @param text  the text of the label
     * @return the newly-created label
     */
    protected Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.LEFT);
        label.setText(text);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    /**
     * Creates the first version of the page. This is a placeholder page which
     * notified the user that the page is not available.
     *
     * @param panel  the panel in which to create the page
     */
    protected void createPageOne(Composite panel) {
        Label l = createLabel(panel, MessageUtil
        GridData gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
        l = createLabel(
                panel,
                MessageUtil
        gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
        l = createLabel(
                panel,
                MessageUtil
        gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
    }

    /**
     * Creates the second version of the page. This page might contain more information
     * about the file or other information.
     *
     * @param panel  the panel in which to create the page
     */
    protected void createPageTwo(Composite panel) {
        Label l = createLabel(
                panel,
                MessageUtil
        GridData gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
        l = createLabel(
                panel,
                MessageUtil
        gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
        l = createLabel(panel, MessageUtil.getString("Additional_information")); //$NON-NLS-1$
        gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
        l = createLabel(
                panel,
                MessageUtil
        gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
        l = createLabel(panel, MessageUtil
        gd = (GridData) l.getLayoutData();
        gd.horizontalSpan = 2;
        gd.grabExcessHorizontalSpace = true;
    }

    /**
     * Returns which page to display. This implementation
     * answers 1 if the size of the Readme file is less than 256 bytes
     * and 2 otherwise.
     *
     * @return the index of the page to display
     */
    protected int getPageIndex() {
        IResource resource = (IResource) getElement();

        if (resource.getType() == IResource.FILE) {
            int length = 0;
            IFile file = (IFile) resource;
            try (InputStream contentStream = file.getContents();
                    Reader in = new InputStreamReader(contentStream);) {

                int chunkSize = contentStream.available();
                StringBuilder buffer = new StringBuilder(chunkSize);
                char[] readBuffer = new char[chunkSize];
                int n = in.read(readBuffer);

                while (n > 0) {
                    buffer.append(readBuffer);
                    n = in.read(readBuffer);
                }

                contentStream.close();
                length = buffer.length();
            } catch (CoreException e) {
                length = 0;
            } catch (IOException e) {
            }

            if (length < 256)
                return 1;
            return 2;
        }

        return 0;
    }

    /** (non-Javadoc)
     * Method declared on PreferencePage
     */
    @Override
