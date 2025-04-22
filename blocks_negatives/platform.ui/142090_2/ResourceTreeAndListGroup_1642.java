                    IProgressMonitor monitor) {
                for (Object element : elements) {
                    returnValue.add(element);
                }
            }
        };

        try {
            getAllCheckedListItems(passThroughFilter, null);
        } catch (InterruptedException exception) {
            return Collections.EMPTY_LIST;
        }
        return returnValue;

    }

    /**
     *	Returns a flat list of all of the leaf elements.
     *
     *	@return all of the leaf elements.
     */
    public List getAllListItems() {
        final ArrayList returnValue = new ArrayList();

        IElementFilter passThroughFilter = new IElementFilter() {

            @Override
