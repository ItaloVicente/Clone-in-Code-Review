            WizardCollectionElement currentCategory = (WizardCollectionElement) element;
            if (id.equals(currentCategory.getId())) {
                    return currentCategory;
            }
            WizardCollectionElement childCategory = currentCategory.findCategory(id);
            if (childCategory != null) {
                return childCategory;
            }
        }
        return null;
    }

    /**
     * Returns this collection's associated wizard object corresponding to the
     * passed id, or <code>null</code> if such an object could not be found.
     *
     * @param searchId the id to search on
     * @param recursive whether to search recursivly
     * @return the element
     */
    public WorkbenchWizardElement findWizard(String searchId, boolean recursive) {
