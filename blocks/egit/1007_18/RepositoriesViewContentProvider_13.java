			int uriCount = rc.getPushURIs().size();
			if (uriCount == 0 && !rc.getURIs().isEmpty())
				uriCount++;

			if (uriCount > 0 && !rc.getPushRefSpecs().isEmpty()) {
				URIish firstUri;
				if (!rc.getPushURIs().isEmpty())
					firstUri = rc.getPushURIs().get(0);
