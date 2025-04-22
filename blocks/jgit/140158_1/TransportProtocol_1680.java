
package org.eclipse.jgit.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.io.MessageWriter;
import org.eclipse.jgit.util.io.StreamCopyThread;

class TransportLocal extends Transport implements PackTransport {
	static final TransportProtocol PROTO_LOCAL = new TransportProtocol() {
		@Override
		public String getName() {
			return JGitText.get().transportProtoLocal;
		}

		@Override
		public Set<String> getSchemes() {
		}

		@Override
		public boolean canHandle(URIish uri
			if (uri.getPath() == null
					|| uri.getPort() > 0
					|| uri.getUser() != null
					|| uri.getPass() != null
					|| uri.getHost() != null
					|| (uri.getScheme() != null && !getSchemes().contains(uri.getScheme())))
				return false;
			return true;
		}

		@Override
		public Transport open(URIish uri
				throws NoRemoteRepositoryException {
			File localPath = local.isBare() ? local.getDirectory() : local.getWorkTree();
			File path = local.getFS().resolve(localPath
			if (path.isFile())
				return new TransportBundleFile(local

			File gitDir = RepositoryCache.FileKey.resolve(path
			if (gitDir == null)
				throw new NoRemoteRepositoryException(uri
			return new TransportLocal(local
		}

		@Override
		public Transport open(URIish uri) throws NotSupportedException
				TransportException {
			File path = FS.DETECTED.resolve(new File(".")
			if (path.isFile())
				return new TransportBundleFile(uri

			File gitDir = RepositoryCache.FileKey.resolve(path
			if (gitDir == null)
				throw new NoRemoteRepositoryException(uri
						JGitText.get().notFound);
			return new TransportLocal(uri
		}
	};

	private final File remoteGitDir;

	TransportLocal(Repository local
		super(local
		remoteGitDir = gitDir;
	}

	TransportLocal(URIish uri
		super(uri);
		remoteGitDir = gitDir;
	}

	UploadPack createUploadPack(Repository dst) {
		return new UploadPack(dst);
	}

	ReceivePack createReceivePack(Repository dst) {
		return new ReceivePack(dst);
	}

	private Repository openRepo() throws TransportException {
		try {
			return new RepositoryBuilder()
					.setFS(local != null ? local.getFS() : FS.DETECTED)
					.setGitDir(remoteGitDir).build();
		} catch (IOException err) {
			throw new TransportException(uri
		}
	}

	@Override
	public FetchConnection openFetch() throws TransportException {
		final String up = getOptionUploadPack();
			return new ForkLocalFetchConnection();

		UploadPackFactory<Void> upf = new UploadPackFactory<Void>() {
			@Override
			public UploadPack create(Void req
				return createUploadPack(db);
			}
		};
		return new InternalFetchConnection<>(this
	}

	@Override
	public PushConnection openPush() throws TransportException {
		final String rp = getOptionReceivePack();
			return new ForkLocalPushConnection();

		ReceivePackFactory<Void> rpf = new ReceivePackFactory<Void>() {
			@Override
			public ReceivePack create(Void req
				return createReceivePack(db);
			}
		};
		return new InternalPushConnection<>(this
	}

	@Override
	public void close() {
	}

	protected Process spawn(String cmd)
			throws TransportException {
		try {
			ProcessBuilder proc = local.getFS().runInShell(cmd
			proc.directory(remoteGitDir);

			Map<String

			return proc.start();
		} catch (IOException err) {
			throw new TransportException(uri
		}
	}

	class ForkLocalFetchConnection extends BasePackFetchConnection {
		private Process uploadPack;

		private Thread errorReaderThread;

		ForkLocalFetchConnection() throws TransportException {
			super(TransportLocal.this);

			final MessageWriter msg = new MessageWriter();
			setMessageWriter(msg);

			uploadPack = spawn(getOptionUploadPack());

			final InputStream upErr = uploadPack.getErrorStream();
			errorReaderThread = new StreamCopyThread(upErr
			errorReaderThread.start();

			InputStream upIn = uploadPack.getInputStream();
			OutputStream upOut = uploadPack.getOutputStream();

			upIn = new BufferedInputStream(upIn);
			upOut = new BufferedOutputStream(upOut);

			init(upIn
			readAdvertisedRefs();
		}

		@Override
		public void close() {
			super.close();

			if (uploadPack != null) {
				try {
					uploadPack.waitFor();
				} catch (InterruptedException ie) {
				} finally {
					uploadPack = null;
				}
			}

			if (errorReaderThread != null) {
				try {
					errorReaderThread.join();
				} catch (InterruptedException e) {
				} finally {
					errorReaderThread = null;
				}
			}
		}
	}

	class ForkLocalPushConnection extends BasePackPushConnection {
		private Process receivePack;

		private Thread errorReaderThread;

		ForkLocalPushConnection() throws TransportException {
			super(TransportLocal.this);

			final MessageWriter msg = new MessageWriter();
			setMessageWriter(msg);

			receivePack = spawn(getOptionReceivePack());

			final InputStream rpErr = receivePack.getErrorStream();
			errorReaderThread = new StreamCopyThread(rpErr
			errorReaderThread.start();

			InputStream rpIn = receivePack.getInputStream();
			OutputStream rpOut = receivePack.getOutputStream();

			rpIn = new BufferedInputStream(rpIn);
			rpOut = new BufferedOutputStream(rpOut);

			init(rpIn
			readAdvertisedRefs();
		}

		@Override
		public void close() {
			super.close();

			if (receivePack != null) {
				try {
					receivePack.waitFor();
				} catch (InterruptedException ie) {
				} finally {
					receivePack = null;
				}
			}

			if (errorReaderThread != null) {
				try {
					errorReaderThread.join();
				} catch (InterruptedException e) {
				} finally {
					errorReaderThread = null;
				}
			}
		}
	}
}
