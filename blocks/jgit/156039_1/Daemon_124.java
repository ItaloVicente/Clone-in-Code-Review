
package org.eclipse.jgit.niofs.internal.daemon.git;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.Deflater;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.ketch.KetchLeader;
import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
import org.eclipse.jgit.internal.ketch.KetchPreReceive;
import org.eclipse.jgit.internal.ketch.KetchText;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
import org.eclipse.jgit.niofs.internal.util.DescriptiveRunnable;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.jgit.niofs.internal.daemon.common.PortUtil.validateOrGetNew;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

public class Daemon {

    private static final Logger LOG = LoggerFactory.getLogger(Daemon.class);

    private static final int BACKLOG = 5;

    private InetSocketAddress myAddress;

    private final DaemonService[] services;

    private final AtomicBoolean run = new AtomicBoolean(false);

    private int timeout;

    private volatile RepositoryResolver<DaemonClient> repositoryResolver;

    private volatile UploadPackFactory<DaemonClient> uploadPackFactory;

    private volatile ReceivePackFactory<DaemonClient> receivePackFactory;

    private ServerSocket listenSock = null;

    private ExecutorService executorService;

    private final Executor acceptThreadPool;

    public Daemon(final InetSocketAddress addr
                  final Executor acceptThreadPool
                  final ExecutorService executorService) {
        this(addr
             acceptThreadPool
             executorService
             null);
    }

    public Daemon(final InetSocketAddress addr
                  final Executor acceptThreadPool
                  final ExecutorService executorService
                  final KetchLeaderCache leaders) {
        myAddress = addr;
        this.acceptThreadPool = checkNotNull("acceptThreadPool"
                                             acceptThreadPool);

        this.executorService = executorService;

        repositoryResolver = (RepositoryResolver<DaemonClient>) RepositoryResolver.NONE;

        uploadPackFactory = (req
            final UploadPack up = new UploadPack(db);
            up.setTimeout(getTimeout());
            up.setRefFilter(new HiddenBranchRefFilter());
            final PackConfig config = new PackConfig(db);
            config.setCompressionLevel(Deflater.BEST_COMPRESSION);
            up.setPackConfig(config);

            return up;
        };

        final ReceivePackFactory<DaemonClient> factory = (req
            final ReceivePack rp = new KetchCustomReceivePack(db);

            final InetAddress peer = req.getRemoteAddress();
            String host = peer.getCanonicalHostName();
            if (host == null) {
                host = peer.getHostAddress();
            }
            final String name = "anonymous";
            final String email = name + "@" + host;
            rp.setRefLogIdent(new PersonIdent("system"
                                              "system"
                                              new Date(1L)
                                              TimeZone.getDefault()));
            rp.setTimeout(getTimeout());

            rp.setPreReceiveHook((rp12
                                         System.out.println("[" + addr.getHostString() + "]" + " onPreReceive!"));
            rp.setPostReceiveHook((rp1
                                          System.out.println("[" + addr.getHostString() + "]" + " onPostReceive!"));

            return rp;
        };

        if (true) {
            receivePackFactory = factory;
        } else {
            receivePackFactory = (req
                final ReceivePack rp = factory.create(req
                                                      repo);
                final KetchLeader leader;
                try {
                    leader = leaders.get(repo);
                } catch (URISyntaxException err) {
                    throw new ServiceNotEnabledException(
                            KetchText.get().invalidFollowerUri
                            err);
                }
                rp.setPreReceiveHook(new KetchPreReceive(leader));
                return rp;
            };
        }

        services = new DaemonService[]{new DaemonService("upload-pack"
                                                         "uploadpack") {
            {
                setEnabled(true);
            }

            @Override
            protected void execute(final DaemonClient dc
                                   final Repository db) throws IOException
                    ServiceNotEnabledException
                    ServiceNotAuthorizedException {
                final UploadPack up = uploadPackFactory.create(dc
                                                               db);
                final InputStream in = dc.getInputStream();
                final OutputStream out = dc.getOutputStream();
                up.upload(in
                          out
                          null);
            }
        }
                             "receivepack") {
            {
                setEnabled(true);
            }

            @Override
            protected void execute(final DaemonClient dc
                                   final Repository db) throws IOException
                    ServiceNotEnabledException
                    ServiceNotAuthorizedException {
                final ReceivePack rp = receivePackFactory.create(dc
                                                                 db);
                final InputStream in = dc.getInputStream();
                final OutputStream out = dc.getOutputStream();
                rp.receive(in
                           out
                           null);
            }
        }};
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
                                               BACKLOG
                                               listenAddress);
        } catch (IOException e) {
            throw new IOException("Failed to open server socket for " + listenAddress + ":" + listenPort
        }
        if (listenSock.getLocalPort() != listenPort) {
            LOG.error("Git original port {} not available
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
                              String name)
            throws ServiceMayNotContinueException {
        name = name.replace('\\'
                            '/');

        if (!name.startsWith("/")) {
            return null;
        }

        try {
            return repositoryResolver.open(client
                                           name.substring(1));
        } catch (RepositoryNotFoundException | ServiceNotAuthorizedException | ServiceNotEnabledException e) {
            return null;
        }
    }
}
