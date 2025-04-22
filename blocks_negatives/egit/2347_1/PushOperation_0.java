		for (URIish uri : this.specification.getURIs()) {
			for (RemoteRefUpdate update : this.specification.getRefUpdates(uri))
				if (update.getStatus() != Status.NOT_ATTEMPTED)
					throw new IllegalStateException(
							CoreText.RemoteRefUpdateCantBeReused);
		}
