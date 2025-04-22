        }
    }

    /**
     * Creates a new instance of the class.
     *
     * @param parentShell shell to parent the dialog on
     * @param resources resources to display in the dialog
     */
    public ResourceListSelectionDialog(Shell parentShell, IResource[] resources) {
        super(parentShell);
        gatherResourcesDynamically = false;
        initDescriptors(resources);
    }

    /**
     * Creates a new instance of the class.  When this constructor is used to
     * create the dialog, resources will be gathered dynamically as the pattern
     * string is specified.  Only resources of the given types that match the
     * pattern string will be listed.  To further filter the matching resources,
     * @see #select(IResource)
     *
     * @param parentShell shell to parent the dialog on
     * @param container container to get resources from
     * @param typeMask mask containing IResource types to be considered
     */
    public ResourceListSelectionDialog(Shell parentShell, IContainer container,
            int typeMask) {
        super(parentShell);
        this.container = container;
        this.typeMask = typeMask;
    }

    /**
     * Adjust the pattern string for matching.
     */
    protected String adjustPattern() {
        String text = pattern.getText().trim();
            return text.substring(0, text.length() - 1);
        }
        }
        return text;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
     */
    @Override
