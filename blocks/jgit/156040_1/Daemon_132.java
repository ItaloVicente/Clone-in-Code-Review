		if (true) {
			receivePackFactory = factory;
		} else {
			receivePackFactory = (req
				final ReceivePack rp = factory.create(req
				final KetchLeader leader;
				try {
					leader = leaders.get(repo);
				} catch (URISyntaxException err) {
					throw new ServiceNotEnabledException(KetchText.get().invalidFollowerUri
				}
				rp.setPreReceiveHook(new KetchPreReceive(leader));
				return rp;
			};
		}

		services = new DaemonService[] { new DaemonService("upload-pack"
			{
				setEnabled(true);
			}

			@Override
			protected void execute(final DaemonClient dc
					throws IOException
				final UploadPack up = uploadPackFactory.create(dc
				final InputStream in = dc.getInputStream();
				final OutputStream out = dc.getOutputStream();
				up.upload(in
			}
		}
			{
				setEnabled(true);
			}

			@Override
			protected void execute(final DaemonClient dc
					throws IOException
				final ReceivePack rp = receivePackFactory.create(dc
				final InputStream in = dc.getInputStream();
				final OutputStream out = dc.getOutputStream();
				rp.receive(in
			}
		} };
	}

	public synchronized InetSocketAddress getAddress() {
		return myAddress;
	}

	public synchronized DaemonService getService(String name) {
		if (!name.startsWith("git-")) {
			name = "git-" + name;
		}
		for (final DaemonService s : services) {
			if (s.getCommandName().equals(name)) {
				return s;
			}
		}
		return null;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(final int seconds) {
		timeout = seconds;
	}

	public void setRepositoryResolver(RepositoryResolver<DaemonClient> resolver) {
		repositoryResolver = resolver;
	}

	@SuppressWarnings("unchecked")
	public void setUploadPackFactory(UploadPackFactory<DaemonClient> factory) {
		if (factory != null) {
			uploadPackFactory = factory;
		} else {
			uploadPackFactory = (UploadPackFactory<DaemonClient>) UploadPackFactory.DISABLED;
		}
	}

	public synchronized void start() throws IOException {
		if (run.get()) {
			throw new IllegalStateException(JGitText.get().daemonAlreadyRunning);
		}

		InetAddress listenAddress = myAddress != null ? myAddress.getAddress() : null;
		int listenPort = myAddress != null ? myAddress.getPort() : 0;

		try {
			this.listenSock = new ServerSocket(validateOrGetNew(listenPort)
		} catch (IOException e) {
			throw new IOException("Failed to open server socket for " + listenAddress + ":" + listenPort
		}
		if (listenSock.getLocalPort() != listenPort) {
			LOG.error("Git original port {} not available
					listenSock.getLocalPort());
		}
		myAddress = (InetSocketAddress) listenSock.getLocalSocketAddress();

		run.set(true);
		acceptThreadPool.execute(new DescriptiveRunnable() {
			@Override
			public String getDescription() {
				return "Git-Daemon-Accept";
			}

			@Override
			public void run() {
				while (isRunning() && !Thread.currentThread().isInterrupted()) {
					try {
						listenSock.setSoTimeout(5000);
						startClient(listenSock.accept());
					} catch (InterruptedIOException e) {
					} catch (IOException e) {
						break;
					}
				}

				stop();
			}
		});
	}

	public boolean isRunning() {
		return run.get();
	}

	public synchronized void stop() {
		if (run.getAndSet(false)) {
			try {
				listenSock.close();
			} catch (IOException e) {
			}
		}
	}

	private void startClient(final Socket s) {
		final DaemonClient dc = new DaemonClient(this);

		final SocketAddress peer = s.getRemoteSocketAddress();
		if (peer instanceof InetSocketAddress) {
			dc.setRemoteAddress(((InetSocketAddress) peer).getAddress());
		}

		executorService.execute(new DescriptiveRunnable() {
			@Override
			public String getDescription() {
				return "Git-Daemon-Client " + peer.toString();
			}

			@Override
			public void run() {
				try {
					dc.execute(s);
				} catch (ServiceNotEnabledException | ServiceNotAuthorizedException | IOException e) {
				} finally {
					try {
						s.getInputStream().close();
					} catch (IOException e) {
					}
					try {
						s.getOutputStream().close();
					} catch (IOException e) {
					}
				}
			}
		});
	}

	synchronized DaemonService matchService(final String cmd) {
		for (final DaemonService d : services) {
			if (d.handles(cmd)) {
				return d;
			}
		}
		return null;
	}

	Repository openRepository(DaemonClient client
		name = name.replace('\\'

		if (!name.startsWith("/")) {
			return null;
		}

		try {
			return repositoryResolver.open(client
		} catch (RepositoryNotFoundException | ServiceNotAuthorizedException | ServiceNotEnabledException e) {
			return null;
		}
	}
