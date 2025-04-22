                return currentCategory.findChildCollection(searchPath
                        .removeFirstSegments(1));
            }
        }

        return null;
    }

    /**
     * Returns the wizard category corresponding to the passed
     * id, or <code>null</code> if such an object could not be found.
     * This recurses through child categories.
     *
     * @param id the id for the child category
     * @return the category, or <code>null</code> if not found
     * @since 3.1
     */
    public WizardCollectionElement findCategory(String id) {
