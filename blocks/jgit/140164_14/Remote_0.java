			} else {
				switch (command) {
				{
					RemoteAddCommand cmd = git.remoteAdd();
					cmd.setName(name);
					cmd.setUri(new URIish(uri));
					cmd.call();
					break;
