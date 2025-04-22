
package org.eclipse.jgit.transport;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Ref;

public abstract class BaseConnection implements Connection {
	private Map<String

	private String peerUserAgent;

	private boolean startedOperation;

	private Writer messageWriter;

	@Override
	public Map<String
		return advertisedRefs;
	}

	@Override
	public final Collection<Ref> getRefs() {
		return advertisedRefs.values();
	}

	@Override
	public final Ref getRef(String name) {
		return advertisedRefs.get(name);
	}

	@Override
	public String getMessages() {
	}

	@Override
	public String getPeerUserAgent() {
		return peerUserAgent;
	}

	protected void setPeerUserAgent(String agent) {
		peerUserAgent = agent;
	}

	@Override
	public abstract void close();

	protected void available(Map<String
		advertisedRefs = Collections.unmodifiableMap(all);
	}

	protected void markStartedOperation() throws TransportException {
		if (startedOperation)
			throw new TransportException(
					JGitText.get().onlyOneOperationCallPerConnectionIsSupported);
		startedOperation = true;
	}

	protected Writer getMessageWriter() {
		if (messageWriter == null)
			setMessageWriter(new StringWriter());
		return messageWriter;
	}

	protected void setMessageWriter(Writer writer) {
		if (messageWriter != null)
			throw new IllegalStateException(JGitText.get().writerAlreadyInitialized);
		messageWriter = writer;
	}
}
