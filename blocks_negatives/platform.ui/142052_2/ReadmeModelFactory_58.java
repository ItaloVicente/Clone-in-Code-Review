    /**
     * Returns a list of all sections in this readme file.
     *
     * @param file  the file for which to return section heading and subheadings
     * @return A list containing headings and subheadings
     */
    public AdaptableList getSections(IFile file) {
        MarkElement[] topLevel = getToc(file);
        AdaptableList list = new AdaptableList();
        for (int i = 0; i < topLevel.length; i++) {
            addSections(list, topLevel[i]);
        }
        return list;
    }
