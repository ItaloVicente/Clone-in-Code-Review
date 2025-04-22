				RemoteAddCommand cmd = git.remoteAdd();
				cmd.setName(name);
				cmd.setUri(new URIish(uri));
				cmd.call();
				RemoteRemoveCommand cmd = git.remoteRemove();
				cmd.setRemoteName(name);
				cmd.call();
				RemoteSetUrlCommand cmd = git.remoteSetUrl();
				cmd.setRemoteName(name);
				cmd.setRemoteUri(new URIish(uri));
				cmd.setUriType(push ? UriType.PUSH : UriType.FETCH);
				cmd.call();
				Fetch fetch = new Fetch();
				fetch.init(db, gitdir);

				StringWriter osw = new StringWriter();
				fetch.outw = new ThrowingPrintWriter(osw);
				StringWriter esw = new StringWriter();
				fetch.errw = new ThrowingPrintWriter(esw);

				List<String> fetchArgs = new ArrayList<>();
				if (verbose) {
				}
				if (prune) {
				}
				if (name != null) {
					fetchArgs.add(name);
				}

				fetch.execute(fetchArgs.toArray(new String[0]));

				fetch.outw.flush();
				fetch.errw.flush();
				outw.println(osw.toString());
				errw.println(esw.toString());
