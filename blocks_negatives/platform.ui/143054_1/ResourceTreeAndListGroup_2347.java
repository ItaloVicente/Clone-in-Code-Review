            }
        } catch (InterruptedException exception) {
            return Collections.EMPTY_LIST;
        }
        return returnValue;

    }

    /**
     *	Returns a list of all of the items that are white checked.
     * 	Any folders that are white checked are added and then any files
     *  from white checked folders are added.
     *
     *	@return the list of all of the items that are white checked
     */
    public List getAllWhiteCheckedItems() {
        List result = new ArrayList();
