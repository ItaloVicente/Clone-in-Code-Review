		} catch (URISyntaxException e) {
			Activator.handleError(e.getMessage(), e, false);
			setErrorMessage(e.getMessage());
		}
		for (String aUri : uris) {
			uriCombo.add(aUri);
			changeRefs.put(aUri, new ChangeList(repository, aUri));
