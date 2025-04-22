    }

    /**
     * Set the clipboard contents. Prompt to retry if clipboard is busy.
     *
     * @param resources the resources to copy to the clipboard
     * @param fileNames file names of the resources to copy to the clipboard
     * @param names string representation of all names
     */
    private void setClipboard(IResource[] resources, String[] fileNames,
            String names) {
        try {
            if (fileNames.length > 0) {
