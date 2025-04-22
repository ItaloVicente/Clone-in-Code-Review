        }

        return getSortedEntries(getFilteredEntries(entry.getChildEntries()));
    }

    /**
     * Returns the child entries of the given category
     *
     * @param category The category to search
     *
     * @return the children of the given category (element type
     *         <code>IPropertySheetEntry</code>)
     */
    private List getChildren(PropertySheetCategory category) {
        return getSortedEntries(getFilteredEntries(category.getChildEntries()));
    }

    @Override
