        noDefaultAndApplyButton();
        Composite panel = createComposite(parent, 2);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IReadmeConstants.PROPERTY_PAGE_CONTEXT);


        IResource resource = (IResource) getElement();
        IStatus result = null;
        if (resource.getType() == IResource.FILE) {
            Label label = createLabel(panel, MessageUtil.getString("File_name")); //$NON-NLS-1$
            label = createLabel(panel, resource.getName());
            grabExcessSpace(label);

            createLabel(panel, MessageUtil.getString("Path")); //$NON-NLS-1$
            label = createLabel(panel, resource.getFullPath().setDevice(null)
                    .toString());
            grabExcessSpace(label);

            createLabel(panel, MessageUtil.getString("Size")); //$NON-NLS-1$
            IFile file = (IFile) resource;
            try (InputStream contentStream = file.getContents();
                Reader in = new InputStreamReader(contentStream)){

                int chunkSize = contentStream.available();
                StringBuilder buffer = new StringBuilder(chunkSize);
                char[] readBuffer = new char[chunkSize];
                int n = in.read(readBuffer);

                while (n > 0) {
                    buffer.append(readBuffer);
                    n = in.read(readBuffer);
                }

                contentStream.close();
                label = createLabel(panel, Integer.toString(buffer.length()));
            } catch (CoreException e) {
                result = e.getStatus();
                String message = result.getMessage();
                if (message == null)
                    label = createLabel(panel, MessageUtil.getString("<Unknown>")); //$NON-NLS-1$
                else
                    label = createLabel(panel, message);
            } catch (IOException e) {
                label = createLabel(panel, MessageUtil.getString("<Unknown>")); //$NON-NLS-1$
            }
            grabExcessSpace(label);
            createLabel(panel, MessageUtil.getString("Number_of_sections")); //$NON-NLS-1$
            IAdaptable sections = getSections(resource);
            if (sections instanceof AdaptableList) {
                AdaptableList list = (AdaptableList) sections;
                label = createLabel(panel, String.valueOf(list.size()));
                grabExcessSpace(label);
            }
        }

        Label label = createLabel(panel, MessageUtil
        grabExcessSpace(label);
        GridData gd = (GridData) label.getLayoutData();
        gd.horizontalSpan = 2;
        return new Canvas(panel, 0);
    }

    /**
     * Utility method that creates a new label and sets up its layout data.
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
     * Returns the readme sections for this resource, or null
     * if not applicable (resource is not a readme file).
     */
    private AdaptableList getSections(IAdaptable adaptable) {
        if (adaptable instanceof IFile)
            return ReadmeModelFactory.getInstance().getSections(
                    (IFile) adaptable);
        return null;
    }

    /**
     * Sets this control to grab any excess horizontal space
     * left in the window.
     *
     * @param control  the control for which to grab excess space
     */
    private void grabExcessSpace(Control control) {
        GridData gd = (GridData) control.getLayoutData();
        if (gd != null) {
            gd.grabExcessHorizontalSpace = true;
        }
    }

    /** (non-Javadoc)
     * Method declared on PreferencePage
     */
    @Override
