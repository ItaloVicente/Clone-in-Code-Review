			spec = new PushOperationSpecification();

			List<URIish> urisToPush = new ArrayList<>();
			for (URIish uri : config.getPushURIs())
				urisToPush.add(uri);
			if (urisToPush.isEmpty() && !config.getURIs().isEmpty())
				urisToPush.add(config.getURIs().get(0));
