        return result;
    }

    /**
     *	Go through the children of  the passed parent and answer the child
     *	with the passed name.  If no such child is found then return null.
     *
     *	@return org.eclipse.ui.internal.model.WizardCollectionElement
     *	@param parent org.eclipse.ui.internal.model.WizardCollectionElement
     *	@param id java.lang.String
     */
    protected WizardCollectionElement getChildWithID(
            WizardCollectionElement parent, String id) {
