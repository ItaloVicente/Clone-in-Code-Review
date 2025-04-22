        return true;
    }

    /**
     * Sets the id of the category of wizards to show or <code>null</code> to
     * show all categories. If no entries can be found with this id then all
     * categories are shown.
     *
     * @param id may be <code>null</code>.
     */
    public void setCategoryId(String id) {
        categoryId = id;
    }

    /**
     * Sets the projects only flag. If <code>true</code> only projects will
     * be shown in this wizard.
     * @param b if only projects should be shown
     */
    public void setProjectsOnly(boolean b) {
        projectsOnly = b;
    }

    @Override
