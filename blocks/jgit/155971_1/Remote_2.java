				return;
			}
			switch (command) {
				RemoteAddCommand add = git.remoteAdd();
				add.setName(name);
				add.setUri(new URIish(uri));
				add.call();
				break;
				RemoteRemoveCommand rm = git.remoteRemove();
				rm.setRemoteName(name);
				rm.call();
				break;
				RemoteSetUrlCommand remoteSetUrl = git.remoteSetUrl();
				remoteSetUrl.setRemoteName(name);
				remoteSetUrl.setRemoteUri(new URIish(uri));
				remoteSetUrl.setUriType(push ? UriType.PUSH : UriType.FETCH);
				remoteSetUrl.call();
				break;
