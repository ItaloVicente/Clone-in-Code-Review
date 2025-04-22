	    for (TreeItem rootItem : rootItems) {
		if (rootItem.getText() == null || rootItem.getText().equals("")) {
		    continue;
		}
		if (text && !rootItem.getText().startsWith(tlp.getColorName())) {
		    fail("Wrong text: " + rootItem.getText());
