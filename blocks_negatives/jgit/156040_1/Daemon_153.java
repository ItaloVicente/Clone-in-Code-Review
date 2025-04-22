        if (true) {
            receivePackFactory = factory;
        } else {
            receivePackFactory = (req, repo) -> {
                final ReceivePack rp = factory.create(req,
                                                      repo);
                final KetchLeader leader;
                try {
                    leader = leaders.get(repo);
                } catch (URISyntaxException err) {
                    throw new ServiceNotEnabledException(
                            KetchText.get().invalidFollowerUri,
                            err);
                }
                rp.setPreReceiveHook(new KetchPreReceive(leader));
                return rp;
            };
        }

        services = new DaemonService[]{new DaemonService("upload-pack",
                                                         "uploadpack") {
            {
                setEnabled(true);
            }

            @Override
            protected void execute(final DaemonClient dc,
                                   final Repository db) throws IOException,
                    ServiceNotEnabledException,
                    ServiceNotAuthorizedException {
                final UploadPack up = uploadPackFactory.create(dc,
                                                               db);
                final InputStream in = dc.getInputStream();
                final OutputStream out = dc.getOutputStream();
                up.upload(in,
                          out,
                          null);
            }
        }, new DaemonService("receive-pack",
                             "receivepack") {
            {
                setEnabled(true);
            }

            @Override
            protected void execute(final DaemonClient dc,
                                   final Repository db) throws IOException,
                    ServiceNotEnabledException,
                    ServiceNotAuthorizedException {
                final ReceivePack rp = receivePackFactory.create(dc,
                                                                 db);
                final InputStream in = dc.getInputStream();
                final OutputStream out = dc.getOutputStream();
                rp.receive(in,
                           out,
                           null);
            }
        }};
    }

    /**
     * @return the address connections are received on.
     */
    public synchronized InetSocketAddress getAddress() {
        return myAddress;
    }

    /**
     * Lookup a supported service so it can be reconfigured.
     * @param name name of the service; e.g. "receive-pack"/"git-receive-pack" or
     * "upload-pack"/"git-upload-pack".
     * @return the service; null if this daemon implementation doesn't support
     * the requested service type.
     */
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

    /**
     * @return timeout (in seconds) before aborting an IO operation.
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Set the timeout before willing to abort an IO call.
     * @param seconds number of seconds to wait (with no data transfer occurring)
     * before aborting an IO read or write operation with the
     * connected client.
     */
    public void setTimeout(final int seconds) {
        timeout = seconds;
    }

    /**
     * Sets the resolver that locates repositories by name.
     * @param resolver the resolver instance.
     */
    public void setRepositoryResolver(RepositoryResolver<DaemonClient> resolver) {
        repositoryResolver = resolver;
    }

    /**
     * Sets the factory that constructs and configures the per-request UploadPack.
     * @param factory the factory. If null upload-pack is disabled.
     */
    @SuppressWarnings("unchecked")
    public void setUploadPackFactory(UploadPackFactory<DaemonClient> factory) {
        if (factory != null) {
            uploadPackFactory = factory;
        } else {
            uploadPackFactory = (UploadPackFactory<DaemonClient>) UploadPackFactory.DISABLED;
        }
    }

    /**
     * Starts this daemon listening for connections on a thread supplied by the executor service given to the
     * constructor. The daemon can be stopped by a call to {@link #stop()} or by shutting down the ExecutorService.
     * @throws IOException the server socket could not be opened.
     * @throws IllegalStateException the daemon is already running.
     */
    public synchronized void start() throws IOException {
        if (run.get()) {
            throw new IllegalStateException(JGitText.get().daemonAlreadyRunning);
        }

        InetAddress listenAddress = myAddress != null ? myAddress.getAddress() : null;
        int listenPort = myAddress != null ? myAddress.getPort() : 0;

        try {
            this.listenSock = new ServerSocket(validateOrGetNew(listenPort),
                                               BACKLOG,
                                               listenAddress);
        } catch (IOException e) {
            throw new IOException("Failed to open server socket for " + listenAddress + ":" + listenPort, e);
        }
        if (listenSock.getLocalPort() != listenPort) {
            LOG.error("Git original port {} not available, new free port {} assigned.", listenPort, listenSock.getLocalPort());
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

    /**
     * @return true if this daemon is receiving connections.
     */
    public boolean isRunning() {
        return run.get();
    }

    /**
     * Stops this daemon. It is safe to call this method on a daemon which is already stopped, in which case the call
     * has no effect.
     */
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

    Repository openRepository(DaemonClient client,
                              String name)
            throws ServiceMayNotContinueException {
        name = name.replace('\\',
                            '/');

        if (!name.startsWith("/")) {
            return null;
        }

        try {
            return repositoryResolver.open(client,
                                           name.substring(1));
        } catch (RepositoryNotFoundException | ServiceNotAuthorizedException | ServiceNotEnabledException e) {
            return null;
        }
    }
