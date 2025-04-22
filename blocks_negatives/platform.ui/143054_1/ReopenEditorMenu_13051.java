    /**
     * Return a string suitable for a file MRU list.  This should not be called
     * outside the framework.
     *
     * @param index the index in the MRU list
     * @param name the file name
     * @param toolTip potentially the path
     * @param rtl should it be right-to-left
     * @return a string suitable for an MRU file menu
     */
    public static String calcText(int index, String name, String toolTip, boolean rtl) {
        StringBuilder sb = new StringBuilder();

        int mnemonic = index + 1;
        StringBuilder nm = new StringBuilder();
        nm.append(mnemonic);
        if (mnemonic <= MAX_MNEMONIC_SIZE) {
        	nm.insert(nm.length() - (mnemonic + "").length(), '&'); //$NON-NLS-1$
        }
