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
					remoteSetUrl
							.setUriType(push ? UriType.PUSH : UriType.FETCH);
					remoteSetUrl.call();
					break;
					Fetch fetch = new Fetch();
					fetch.init(db
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
					break;
				default:
					throw new JGitInternalException(MessageFormat
							.format(CLIText.get().unknownSubcommand
				}
