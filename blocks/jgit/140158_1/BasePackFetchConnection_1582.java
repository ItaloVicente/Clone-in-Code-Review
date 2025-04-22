
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.RemoteRepositoryException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.io.InterruptTimer;
import org.eclipse.jgit.util.io.TimeoutInputStream;
import org.eclipse.jgit.util.io.TimeoutOutputStream;

abstract class BasePackConnection extends BaseConnection {

	protected final Repository local;

	protected final URIish uri;

	protected final Transport transport;

	protected TimeoutInputStream timeoutIn;

	protected TimeoutOutputStream timeoutOut;

	private InterruptTimer myTimer;

	protected InputStream in;

	protected OutputStream out;

	protected PacketLineIn pckIn;

	protected PacketLineOut pckOut;

	protected boolean outNeedsEnd;

	protected boolean statelessRPC;

	private final Set<String> remoteCapablities = new HashSet<>();

	protected final Set<ObjectId> additionalHaves = new HashSet<>();

	BasePackConnection(PackTransport packTransport) {
		transport = (Transport) packTransport;
		local = transport.local;
		uri = transport.uri;
	}

	protected final void init(InputStream myIn
		final int timeout = transport.getTimeout();
		if (timeout > 0) {
			final Thread caller = Thread.currentThread();
			if (myTimer == null) {
			}
			timeoutIn = new TimeoutInputStream(myIn
			timeoutOut = new TimeoutOutputStream(myOut
			timeoutIn.setTimeout(timeout * 1000);
			timeoutOut.setTimeout(timeout * 1000);
			myIn = timeoutIn;
			myOut = timeoutOut;
		}

		in = myIn;
		out = myOut;

		pckIn = new PacketLineIn(in);
		pckOut = new PacketLineOut(out);
		outNeedsEnd = true;
	}

	protected void readAdvertisedRefs() throws TransportException {
		try {
			readAdvertisedRefsImpl();
		} catch (TransportException err) {
			close();
			throw err;
		} catch (IOException | RuntimeException err) {
			close();
			throw new TransportException(err.getMessage()
		}
	}

	private void readAdvertisedRefsImpl() throws IOException {
		final LinkedHashMap<String
		for (;;) {
			String line;

			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (avail.isEmpty())
					throw noRepository();
				throw eof;
			}
			if (line == PacketLineIn.END)
				break;

				throw new RemoteRepositoryException(uri
			}

			if (avail.isEmpty()) {
				final int nul = line.indexOf('\0');
				if (nul >= 0) {
						remoteCapablities.add(c);
					line = line.substring(0
				}
			}

			if (line.length() < 41 || line.charAt(40) != ' ') {
				throw invalidRefAdvertisementLine(line);
			}
			String name = line.substring(41
				continue;
			}

			final ObjectId id;
			try {
				id  = ObjectId.fromString(line.substring(0
			} catch (InvalidObjectIdException e) {
				throw invalidRefAdvertisementLine(line);
			}
				additionalHaves.add(id);
				name = name.substring(0
				final Ref prior = avail.get(name);
				if (prior == null)
					throw new PackProtocolException(uri
							JGitText.get().advertisementCameBefore

				if (prior.getPeeledObjectId() != null)

				avail.put(name
						Ref.Storage.NETWORK
			} else {
				final Ref prior = avail.put(name
						Ref.Storage.NETWORK
				if (prior != null)
					throw duplicateAdvertisement(name);
			}
		}
		available(avail);
	}

	protected TransportException noRepository() {
		return new NoRemoteRepositoryException(uri
	}

	protected boolean isCapableOf(String option) {
		return remoteCapablities.contains(option);
	}

	protected boolean wantCapability(StringBuilder b
		if (!isCapableOf(option))
			return false;
		b.append(' ');
		b.append(option);
		return true;
	}

	protected void addUserAgentCapability(StringBuilder b) {
		String a = UserAgent.get();
		if (a != null && UserAgent.hasAgent(remoteCapablities)) {
			b.append(' ').append(OPTION_AGENT).append('=').append(a);
		}
	}

	@Override
	public String getPeerUserAgent() {
		return UserAgent.getAgent(remoteCapablities
	}

	private PackProtocolException duplicateAdvertisement(String name) {
		return new PackProtocolException(uri
	}

	private PackProtocolException invalidRefAdvertisementLine(String line) {
		return new PackProtocolException(uri
	}

	@Override
	public void close() {
		if (out != null) {
			try {
				if (outNeedsEnd) {
					outNeedsEnd = false;
					pckOut.end();
				}
				out.close();
			} catch (IOException err) {
			} finally {
				out = null;
				pckOut = null;
			}
		}

		if (in != null) {
			try {
				in.close();
			} catch (IOException err) {
			} finally {
				in = null;
				pckIn = null;
			}
		}

		if (myTimer != null) {
			try {
				myTimer.terminate();
			} finally {
				myTimer = null;
				timeoutIn = null;
				timeoutOut = null;
			}
		}
	}

	protected void endOut() {
		if (outNeedsEnd && out != null) {
			try {
				outNeedsEnd = false;
				pckOut.end();
			} catch (IOException e) {
				try {
					out.close();
				} catch (IOException err) {
				} finally {
					out = null;
					pckOut = null;
				}
			}
		}
	}
}
