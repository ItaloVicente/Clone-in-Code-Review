				String remote = proj.remote;
				if (remote == null)
					remote = defaultRemote;
				String remoteUrl = remoteUrls.get(remote);
				if (remoteUrl == null) {
					remoteUrl = baseUri.resolve(remotes.get(remote)).toString();
					if (!remoteUrl.endsWith("/"))
						remoteUrl = remoteUrl + "/";
					remoteUrls.put(remote
				}

