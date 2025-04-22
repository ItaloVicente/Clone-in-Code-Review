        }

        mainPage = new NewWizardSelectionPage(workbench, selection, root,
				primary, projectsOnly);
        addPage(mainPage);
    }

    /**
     * Returns the id of the category of wizards to show or <code>null</code>
     * to show all categories. If no entries can be found with this id then all
     * categories are shown.
     *
     * @return String or <code>null</code>.
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Returns the child collection element for the given id
     */
    private IWizardCategory getChildWithID(
            IWizardCategory parent, String id) {
