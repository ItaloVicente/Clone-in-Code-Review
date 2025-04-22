    /**
     * Finds and returns the preference node directly
     * below the top at the given path.
     *
     * @param path the path
     * @param top top at the given path
     * @return the node, or <code>null</code> if none
     *
     * @since 3.1
     */
    protected IPreferenceNode find(String path,IPreferenceNode top){
    	 Assert.isNotNull(path);
         StringTokenizer stok = new StringTokenizer(path, separator);
         IPreferenceNode node = top;
         while (stok.hasMoreTokens()) {
             String id = stok.nextToken();
             node = node.findSubNode(id);
             if (node == null) {
