			int uriCount = rc.getPushURIs().size();
			if (!rc.getURIs().isEmpty())
				uriCount++;

			if (uriCount > 0 && !rc.getPushRefSpecs().isEmpty()) {
				URIish firstUri;
				if (!rc.getURIs().isEmpty())
					firstUri = rc.getURIs().get(0);
