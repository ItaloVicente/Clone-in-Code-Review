							final Repository db) throws IOException {
						final InetAddress peer = dc.getRemoteAddress();
						String host = peer.getCanonicalHostName();
						if (host == null)
							host = peer.getHostAddress();
						final ReceivePack rp = new ReceivePack(db);
						final InputStream in = dc.getInputStream();
						final String name = "anonymous";
						final String email = name + "@" + host;
						rp.setRefLogIdent(new PersonIdent(name, email));
						rp.setTimeout(Daemon.this.getTimeout());
						rp.receive(in, dc.getOutputStream(), null);
