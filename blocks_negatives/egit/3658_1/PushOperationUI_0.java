			spec = new PushOperationSpecification();

			List<URIish> urisToPush = new ArrayList<URIish>();
			for (URIish uri : config.getPushURIs())
				urisToPush.add(uri);
			if (urisToPush.isEmpty() && !config.getURIs().isEmpty())
				urisToPush.add(config.getURIs().get(0));

			List<RefSpec> pushRefSpecs = new ArrayList<RefSpec>();
			pushRefSpecs.addAll(config.getPushRefSpecs());
			if (pushRefSpecs.isEmpty())
				pushRefSpecs.add(DEFAULT_PUSH_REF_SPEC);

			for (URIish uri : urisToPush) {
				try {
					spec.addURIRefUpdates(uri, Transport.open(repository, uri)
							.findRemoteRefUpdatesFor(pushRefSpecs));
				} catch (NotSupportedException e) {
					throw new CoreException(Activator.createErrorStatus(
							e.getMessage(), e));
				} catch (IOException e) {
					throw new CoreException(Activator.createErrorStatus(
							e.getMessage(), e));
				}
			}
		}
