
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Collection;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

public class Daemon {
	public static final int DEFAULT_PORT = 9418;

	private static final int BACKLOG = 5;

	private InetSocketAddress myAddress;

	private final DaemonService[] services;

	private final ThreadGroup processors;

	private Acceptor acceptThread;

	private int timeout;

	private PackConfig packConfig;

	private volatile RepositoryResolver<DaemonClient> repositoryResolver;

	volatile UploadPackFactory<DaemonClient> uploadPackFactory;

	volatile ReceivePackFactory<DaemonClient> receivePackFactory;

	public Daemon() {
		this(null);
	}

	@SuppressWarnings("unchecked")
	public Daemon(InetSocketAddress addr) {
		myAddress = addr;

		repositoryResolver = (RepositoryResolver<DaemonClient>) RepositoryResolver.NONE;

		uploadPackFactory = new UploadPackFactory<DaemonClient>() {
			@Override
			public UploadPack create(DaemonClient req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				UploadPack up = new UploadPack(db);
				up.setTimeout(getTimeout());
				up.setPackConfig(getPackConfig());
				return up;
			}
		};

		receivePackFactory = new ReceivePackFactory<DaemonClient>() {
			@Override
			public ReceivePack create(DaemonClient req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack rp = new ReceivePack(db);

				InetAddress peer = req.getRemoteAddress();
				String host = peer.getCanonicalHostName();
				if (host == null)
					host = peer.getHostAddress();
				rp.setRefLogIdent(new PersonIdent(name
				rp.setTimeout(getTimeout());

				return rp;
			}
		};

		services = new DaemonService[] {
				new DaemonService("upload-pack"
					{
						setEnabled(true);
					}

					@Override
					protected void execute(final DaemonClient dc
							final Repository db
							@Nullable Collection<String> extraParameters)
							throws IOException
							ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = uploadPackFactory.create(dc
						InputStream in = dc.getInputStream();
						OutputStream out = dc.getOutputStream();
						if (extraParameters != null) {
							up.setExtraParameters(extraParameters);
						}
						up.upload(in
					}
				}
					{
						setEnabled(false);
					}

					@Override
					protected void execute(final DaemonClient dc
							final Repository db
							@Nullable Collection<String> extraParameters)
							throws IOException
							ServiceNotEnabledException
							ServiceNotAuthorizedException {
						ReceivePack rp = receivePackFactory.create(dc
						InputStream in = dc.getInputStream();
						OutputStream out = dc.getOutputStream();
						rp.receive(in
					}
				} };
	}

	public synchronized InetSocketAddress getAddress() {
		return myAddress;
	}

	public synchronized DaemonService getService(String name) {
		for (DaemonService s : services) {
			if (s.getCommandName().equals(name))
				return s;
		}
		return null;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int seconds) {
		timeout = seconds;
	}

	public PackConfig getPackConfig() {
		return packConfig;
	}

	public void setPackConfig(PackConfig pc) {
		this.packConfig = pc;
	}

	public void setRepositoryResolver(RepositoryResolver<DaemonClient> resolver) {
		repositoryResolver = resolver;
	}

	@SuppressWarnings("unchecked")
	public void setUploadPackFactory(UploadPackFactory<DaemonClient> factory) {
		if (factory != null)
			uploadPackFactory = factory;
		else
			uploadPackFactory = (UploadPackFactory<DaemonClient>) UploadPackFactory.DISABLED;
	}

	public ReceivePackFactory<DaemonClient> getReceivePackFactory() {
		return receivePackFactory;
	}

	@SuppressWarnings("unchecked")
	public void setReceivePackFactory(ReceivePackFactory<DaemonClient> factory) {
		if (factory != null)
			receivePackFactory = factory;
		else
			receivePackFactory = (ReceivePackFactory<DaemonClient>) ReceivePackFactory.DISABLED;
	}

	private class Acceptor extends Thread {

		private final ServerSocket listenSocket;

		private final AtomicBoolean running = new AtomicBoolean(true);

		public Acceptor(ThreadGroup group
			super(group
			this.listenSocket = socket;
		}

		@Override
		public void run() {
			setUncaughtExceptionHandler((thread
			while (isRunning()) {
				try {
					startClient(listenSocket.accept());
				} catch (SocketException e) {
				} catch (IOException e) {
					break;
				}
			}

			terminate();
		}

		private void terminate() {
			try {
				shutDown();
			} finally {
				clearThread();
			}
		}

		public boolean isRunning() {
			return running.get();
		}

		public void shutDown() {
			running.set(false);
			try {
				listenSocket.close();
			} catch (IOException err) {
			}
		}

	}

	public synchronized void start() throws IOException {
		if (acceptThread != null) {
			throw new IllegalStateException(JGitText.get().daemonAlreadyRunning);
		}
		ServerSocket socket = new ServerSocket();
		socket.setReuseAddress(true);
		if (myAddress != null) {
			socket.bind(myAddress
		} else {
			socket.bind(new InetSocketAddress((InetAddress) null
		}
		myAddress = (InetSocketAddress) socket.getLocalSocketAddress();

		acceptThread = new Acceptor(processors
		acceptThread.start();
	}

	private synchronized void clearThread() {
		acceptThread = null;
	}

	public synchronized boolean isRunning() {
		return acceptThread != null && acceptThread.isRunning();
	}

	public synchronized void stop() {
		if (acceptThread != null) {
			acceptThread.shutDown();
		}
	}

	public void stopAndWait() throws InterruptedException {
		Thread acceptor = null;
		synchronized (this) {
			acceptor = acceptThread;
			stop();
		}
		if (acceptor != null) {
			acceptor.join();
		}
	}

	void startClient(Socket s) {
		final DaemonClient dc = new DaemonClient(this);

		final SocketAddress peer = s.getRemoteSocketAddress();
		if (peer instanceof InetSocketAddress)
			dc.setRemoteAddress(((InetSocketAddress) peer).getAddress());

		new Thread(processors
			@Override
			public void run() {
				try {
					dc.execute(s);
				} catch (ServiceNotEnabledException e) {
				} catch (ServiceNotAuthorizedException e) {
				} catch (IOException e) {
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
		}.start();
	}

	synchronized DaemonService matchService(String cmd) {
		for (DaemonService d : services) {
			if (d.handles(cmd))
				return d;
		}
		return null;
	}

	Repository openRepository(DaemonClient client
			throws ServiceMayNotContinueException {
		name = name.replace('\\'

			return null;

		try {
			return repositoryResolver.open(client
		} catch (RepositoryNotFoundException e) {
			return null;
		} catch (ServiceNotAuthorizedException e) {
			return null;
		} catch (ServiceNotEnabledException e) {
			return null;
		}
	}
}
