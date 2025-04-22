
package org.eclipse.jgit.transport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.QuotedString;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.io.MessageWriter;
import org.eclipse.jgit.util.io.StreamCopyThread;

public class TransportGitSsh extends SshTransport implements PackTransport {
	static final TransportProtocol PROTO_SSH = new TransportProtocol() {
		private final String[] schemeNames = { "ssh"

		private final Set<String> schemeSet = Collections
				.unmodifiableSet(new LinkedHashSet<>(Arrays
						.asList(schemeNames)));

		@Override
		public String getName() {
			return JGitText.get().transportProtoSSH;
		}

		@Override
		public Set<String> getSchemes() {
			return schemeSet;
		}

		@Override
		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		@Override
		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.PASS
		}

		@Override
		public int getDefaultPort() {
			return 22;
		}

		@Override
		public boolean canHandle(URIish uri
			if (uri.getScheme() == null) {
				return uri.getHost() != null
					&& uri.getPath() != null
					&& uri.getHost().length() != 0
					&& uri.getPath().length() != 0;
			}
			return super.canHandle(uri
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException {
			return new TransportGitSsh(local
		}

		@Override
		public Transport open(URIish uri) throws NotSupportedException
			return new TransportGitSsh(uri);
		}
	};

	TransportGitSsh(Repository local
		super(local
		initSshSessionFactory();
	}

	TransportGitSsh(URIish uri) {
		super(uri);
		initSshSessionFactory();
	}

	private void initSshSessionFactory() {
		if (useExtSession()) {
			setSshSessionFactory(new SshSessionFactory() {
				@Override
				public RemoteSession getSession(URIish uri2
						CredentialsProvider credentialsProvider
						throws TransportException {
					return new ExtSession();
				}
			});
		}
	}

	@Override
	public FetchConnection openFetch() throws TransportException {
		return new SshFetchConnection();
	}

	@Override
	public PushConnection openPush() throws TransportException {
		return new SshPushConnection();
	}

	String commandFor(String exe) {
		String path = uri.getPath();
			path = (uri.getPath().substring(1));

		final StringBuilder cmd = new StringBuilder();
		cmd.append(exe);
		cmd.append(' ');
		cmd.append(QuotedString.BOURNE.quote(path));
		return cmd.toString();
	}

	void checkExecFailure(int status
			throws TransportException {
		if (status == 127) {
			IOException cause = null;
			if (why != null && why.length() > 0)
				cause = new IOException(why);
			throw new TransportException(uri
					JGitText.get().cannotExecute
		}
	}

	NoRemoteRepositoryException cleanNotFound(NoRemoteRepositoryException nf
			String why) {
		if (why == null || why.length() == 0)
			return nf;

		String path = uri.getPath();
			path = uri.getPath().substring(1);

		final StringBuilder pfx = new StringBuilder();
		pfx.append(QuotedString.BOURNE.quote(path));
		if (why.startsWith(pfx.toString()))
			why = why.substring(pfx.length());

		return new NoRemoteRepositoryException(uri
	}

	private static boolean useExtSession() {
	}

	private class ExtSession implements RemoteSession {
		@Override
		public Process exec(String command
				throws TransportException {

			List<String> args = new ArrayList<>();
			args.add(ssh);
			if (putty
			if (0 < getURI().getPort()) {
				args.add(String.valueOf(getURI().getPort()));
			}
			if (getURI().getUser() != null)
			else
				args.add(getURI().getHost());
			args.add(command);

			ProcessBuilder pb = createProcess(args);
			try {
				return pb.start();
			} catch (IOException err) {
				throw new TransportException(err.getMessage()
			}
		}

		private ProcessBuilder createProcess(List<String> args) {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(args);
			File directory = local != null ? local.getDirectory() : null;
			if (directory != null) {
				pb.environment().put(Constants.GIT_DIR_KEY
						directory.getPath());
			}
			return pb;
		}

		@Override
		public void disconnect() {
		}
	}

	class SshFetchConnection extends BasePackFetchConnection {
		private final Process process;

		private StreamCopyThread errorThread;

		SshFetchConnection() throws TransportException {
			super(TransportGitSsh.this);
			try {
				process = getSession().exec(commandFor(getOptionUploadPack())
						getTimeout());
				final MessageWriter msg = new MessageWriter();
				setMessageWriter(msg);

				final InputStream upErr = process.getErrorStream();
				errorThread = new StreamCopyThread(upErr
				errorThread.start();

				init(process.getInputStream()

			} catch (TransportException err) {
				close();
				throw err;
			} catch (Throwable err) {
				close();
				throw new TransportException(uri
						JGitText.get().remoteHungUpUnexpectedly
			}

			try {
				readAdvertisedRefs();
			} catch (NoRemoteRepositoryException notFound) {
				final String msgs = getMessages();
				checkExecFailure(process.exitValue()
						msgs);
				throw cleanNotFound(notFound
			}
		}

		@Override
		public void close() {
			endOut();

			if (process != null) {
				process.destroy();
			}
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}

			super.close();
		}
	}

	class SshPushConnection extends BasePackPushConnection {
		private final Process process;

		private StreamCopyThread errorThread;

		SshPushConnection() throws TransportException {
			super(TransportGitSsh.this);
			try {
				process = getSession().exec(commandFor(getOptionReceivePack())
						getTimeout());
				final MessageWriter msg = new MessageWriter();
				setMessageWriter(msg);

				final InputStream rpErr = process.getErrorStream();
				errorThread = new StreamCopyThread(rpErr
				errorThread.start();

				init(process.getInputStream()

			} catch (TransportException err) {
				try {
					close();
				} catch (Exception e) {
				}
				throw err;
			} catch (Throwable err) {
				try {
					close();
				} catch (Exception e) {
				}
				throw new TransportException(uri
						JGitText.get().remoteHungUpUnexpectedly
			}

			try {
				readAdvertisedRefs();
			} catch (NoRemoteRepositoryException notFound) {
				final String msgs = getMessages();
				checkExecFailure(process.exitValue()
						msgs);
				throw cleanNotFound(notFound
			}
		}

		@Override
		public void close() {
			endOut();

			if (process != null) {
				process.destroy();
			}
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}

			super.close();
		}
	}
}
