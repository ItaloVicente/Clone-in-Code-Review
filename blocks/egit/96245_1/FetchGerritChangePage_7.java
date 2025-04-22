		String currentUri = uriCombo.getText();
		ChangeList list = changeRefs.get(currentUri);
		if (list == null) {
			list = new ChangeList(repository, currentUri);
			changeRefs.put(currentUri, list);
		}
		preFetch(list);
