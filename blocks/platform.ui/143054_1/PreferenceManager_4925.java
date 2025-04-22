	protected IPreferenceNode find(String path,IPreferenceNode top){
		Assert.isNotNull(path);
		StringTokenizer stok = new StringTokenizer(path, separator);
		IPreferenceNode node = top;
		while (stok.hasMoreTokens()) {
			String id = stok.nextToken();
			node = node.findSubNode(id);
			if (node == null) {
