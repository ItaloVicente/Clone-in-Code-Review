		for (String aUri : uris) {
			uriCombo.add(aUri);
			changeRefs.put(aUri, new ChangeList(repository, aUri));
		}
		if (defaultUri != null) {
			uriCombo.setText(defaultUri);
		} else {
			selectLastUsedUri();
