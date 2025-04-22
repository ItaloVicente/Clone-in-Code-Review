				if (GerritUtil.isGerritRemote(rc)) {
					if (rc.getURIs().size() > 0) {
						uris.add(rc.getURIs().get(0).toPrivateString());
					}
					for (URIish u : rc.getPushURIs()) {
						uris.add(u.toPrivateString());
					}
				}
