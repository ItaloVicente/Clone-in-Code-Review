        return getContents().toArray();
    }

    /**
     * Returns the content
     */
    private ArrayList<OrganizationElement> getContents() {
        if (contents == null)
            contents = new ArrayList<>();
        return contents;
    }

    @Override
