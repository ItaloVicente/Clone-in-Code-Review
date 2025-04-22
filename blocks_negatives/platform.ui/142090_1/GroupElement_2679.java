        return this.toString();
    }

    /**
     * Returns the error message
     */
    public String getErrorMessage() {
        return null;
    }

    /**
     * Returns the subgroups
     */
    private ArrayList<OrganizationElement> getSubGroups() {
        if (subGroups == null)
            subGroups = new ArrayList<>();
        return subGroups;
    }

    /**
     * Returns the users
     */
    private ArrayList<OrganizationElement> getUsers() {
        if (users == null)
            users = new ArrayList<>();
        return users;
    }

    @Override
