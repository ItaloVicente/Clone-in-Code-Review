    /**
     * Indicates that a page change has occurred.  Updates the sync vector.
     */
    protected void onPageChange() {
        if (syncVector != null) {
            Iterator itr = syncVector.iterator();
            while (itr.hasNext()) {
                PageBook pageBook = (PageBook) itr.next();
                syncPageBook(pageBook);
            }
        }
    }
