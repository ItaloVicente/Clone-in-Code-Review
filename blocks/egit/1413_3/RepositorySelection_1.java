		if (isConfigSelected()) {
			List<URIish> pushUris = new ArrayList<URIish>();
			pushUris.addAll(config.getPushURIs());
			if (pushUris.isEmpty())
				pushUris.add(config.getURIs().get(0));
			return pushUris;
		}
