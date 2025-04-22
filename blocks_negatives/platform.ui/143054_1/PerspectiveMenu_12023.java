                list.add(desc);
            }
        }

        return list;
    }

    /**
     * Returns the available list of perspectives to display in the menu.
     * <p>
     * By default, the list contains the perspective shortcuts
     * for the current perspective.
     * </p><p>
     * Subclasses can override this method to return a different list.
     * </p>
     *
     * @return an <code>ArrayList<code> of perspective items <code>IPerspectiveDescriptor</code>
     */
