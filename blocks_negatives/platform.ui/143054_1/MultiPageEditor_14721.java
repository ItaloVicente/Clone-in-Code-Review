    /**
     * Synchronizes each registered pagebook with the editor page.
     */
    protected void sync() {
        if (syncVector != null) {
            Iterator itr = syncVector.iterator();
            while (itr.hasNext()) {
                PageBook pageBook = (PageBook) itr.next();
                syncPageBook(pageBook);
            }
        }
    }
