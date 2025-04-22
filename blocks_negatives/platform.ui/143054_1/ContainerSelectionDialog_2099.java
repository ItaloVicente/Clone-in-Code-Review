        group = new ContainerSelectionGroup(area, listener,
                allowNewContainerName, getMessage(), showClosedProjects);
        if (initialSelection != null) {
            group.setSelectedContainer(initialSelection);
        }

        statusMessage = new Label(area, SWT.WRAP);
        statusMessage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        statusMessage.setFont(parent.getFont());

        return dialogArea;
    }

    /**
     * The <code>ContainerSelectionDialog</code> implementation of this
     * <code>Dialog</code> method builds a list of the selected resource containers
     * for later retrieval by the client and closes this dialog.
     */
    @Override
