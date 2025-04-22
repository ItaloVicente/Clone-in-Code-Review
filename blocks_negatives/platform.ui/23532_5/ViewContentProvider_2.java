     * Removes the temporary intro view from the list so that it cannot be activated except through
     * the introduction command.
     *  
     * @param list the list of view descriptors
     * @return the modified list.
     * @since 3.0
     */
    private ArrayList removeIntroView(ArrayList list) {
        for (Iterator i = list.iterator(); i.hasNext();) {
