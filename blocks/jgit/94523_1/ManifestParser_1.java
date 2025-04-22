				String fetch = remotes.get(remote).fetch;
				if (fetch == null) {
					throw new SAXException(MessageFormat
							.format(RepoText.get().errorNoFetch
				}
				remoteUrl = baseUrl.resolve(fetch).toString();
