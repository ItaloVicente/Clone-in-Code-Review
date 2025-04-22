package org.eclipse.jgit.internal.transport.sshd;

import java.net.SocketAddress;
import java.util.List;

import org.apache.sshd.client.keyverifier.KnownHostsServerKeyVerifier.HostEntryPair;
import org.apache.sshd.client.session.ClientSession;
import org.eclipse.jgit.annotations.NonNull;

public interface ServerKeyLookup {

	@NonNull
	List<HostEntryPair> lookup(ClientSession session
}
