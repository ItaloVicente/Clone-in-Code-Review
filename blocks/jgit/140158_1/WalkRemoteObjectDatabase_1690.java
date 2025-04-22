
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.WalkRemoteObjectDatabase.ROOT_DIR;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;

class WalkPushConnection extends BaseConnection implements PushConnection {
	private final Repository local;

	private final URIish uri;

	final WalkRemoteObjectDatabase dest;

	private final Transport transport;

	private LinkedHashMap<String

	private Map<String

	private Collection<RemoteRefUpdate> packedRefUpdates;

	WalkPushConnection(final WalkTransport walkTransport
			final WalkRemoteObjectDatabase w) {
		transport = (Transport) walkTransport;
		local = transport.local;
		uri = transport.getURI();
		dest = w;
	}

	@Override
	public void push(final ProgressMonitor monitor
			final Map<String
			throws TransportException {
		push(monitor
	}

	@Override
	public void push(final ProgressMonitor monitor
			final Map<String
			throws TransportException {
		markStartedOperation();
		packNames = null;
		newRefs = new TreeMap<>(getRefsMap());
		packedRefUpdates = new ArrayList<>(refUpdates.size());

		final List<RemoteRefUpdate> updates = new ArrayList<>();
		for (RemoteRefUpdate u : refUpdates.values()) {
			final String n = u.getRemoteName();
				u.setStatus(Status.REJECTED_OTHER_REASON);
				u.setMessage(JGitText.get().funnyRefname);
				continue;
			}

			if (AnyObjectId.equals(ObjectId.zeroId()
				deleteCommand(u);
			else
				updates.add(u);
		}

		if (!updates.isEmpty())
			sendpack(updates
		for (RemoteRefUpdate u : updates)
			updateCommand(u);

		if (!updates.isEmpty() && isNewRepository())
			createNewRepository(updates);

		RefWriter refWriter = new RefWriter(newRefs.values()) {
			@Override
			protected void writeFile(String file
					throws IOException {
				dest.writeFile(ROOT_DIR + file
			}
		};
		if (!packedRefUpdates.isEmpty()) {
			try {
				refWriter.writePackedRefs();
				for (RemoteRefUpdate u : packedRefUpdates)
					u.setStatus(Status.OK);
			} catch (IOException err) {
				for (RemoteRefUpdate u : packedRefUpdates) {
					u.setStatus(Status.REJECTED_OTHER_REASON);
					u.setMessage(err.getMessage());
				}
				throw new TransportException(uri
			}
		}

		try {
			refWriter.writeInfoRefs();
		} catch (IOException err) {
			throw new TransportException(uri
		}
	}

	@Override
	public void close() {
		dest.close();
	}

	private void sendpack(final List<RemoteRefUpdate> updates
			final ProgressMonitor monitor) throws TransportException {
		String pathPack = null;
		String pathIdx = null;

		try (PackWriter writer = new PackWriter(transport.getPackConfig()
				local.newObjectReader())) {

			final Set<ObjectId> need = new HashSet<>();
			final Set<ObjectId> have = new HashSet<>();
			for (RemoteRefUpdate r : updates)
				need.add(r.getNewObjectId());
			for (Ref r : getRefs()) {
				have.add(r.getObjectId());
				if (r.getPeeledObjectId() != null)
					have.add(r.getPeeledObjectId());
			}
			writer.preparePack(monitor

			if (writer.getObjectCount() == 0)
				return;

			packNames = new LinkedHashMap<>();
			for (String n : dest.getPackNames())
				packNames.put(n


			if (packNames.remove(packName) != null) {
				dest.writeInfoPacks(packNames.keySet());
				dest.deleteFile(pathIdx);
			}

			String wt = "Put " + base.substring(0
			try (OutputStream os = new BufferedOutputStream(
					dest.writeFile(pathPack
				writer.writePack(monitor
			}

			try (OutputStream os = new BufferedOutputStream(
					dest.writeFile(pathIdx
				writer.writeIndex(os);
			}

			final ArrayList<String> infoPacks = new ArrayList<>();
			infoPacks.add(packName);
			infoPacks.addAll(packNames.keySet());
			dest.writeInfoPacks(infoPacks);

		} catch (IOException err) {
			safeDelete(pathIdx);
			safeDelete(pathPack);

			throw new TransportException(uri
		}
	}

	private void safeDelete(String path) {
		if (path != null) {
			try {
				dest.deleteFile(path);
			} catch (IOException cleanupFailure) {
			}
		}
	}

	private void deleteCommand(RemoteRefUpdate u) {
		final Ref r = newRefs.remove(u.getRemoteName());
		if (r == null) {
			u.setStatus(Status.OK);
			return;
		}

		if (r.getStorage().isPacked())
			packedRefUpdates.add(u);

		if (r.getStorage().isLoose()) {
			try {
				dest.deleteRef(u.getRemoteName());
				u.setStatus(Status.OK);
			} catch (IOException e) {
				u.setStatus(Status.REJECTED_OTHER_REASON);
				u.setMessage(e.getMessage());
			}
		}

		try {
			dest.deleteRefLog(u.getRemoteName());
		} catch (IOException e) {
			u.setStatus(Status.REJECTED_OTHER_REASON);
			u.setMessage(e.getMessage());
		}
	}

	private void updateCommand(RemoteRefUpdate u) {
		try {
			dest.writeRef(u.getRemoteName()
			newRefs.put(u.getRemoteName()
					Storage.LOOSE
			u.setStatus(Status.OK);
		} catch (IOException e) {
			u.setStatus(Status.REJECTED_OTHER_REASON);
			u.setMessage(e.getMessage());
		}
	}

	private boolean isNewRepository() {
		return getRefsMap().isEmpty() && packNames != null
				&& packNames.isEmpty();
	}

	private void createNewRepository(List<RemoteRefUpdate> updates)
			throws TransportException {
		try {
			final byte[] bytes = Constants.encode(ref);
			dest.writeFile(ROOT_DIR + Constants.HEAD
		} catch (IOException e) {
			throw new TransportException(uri
		}

		try {
			final byte[] bytes = Constants.encode(config);
			dest.writeFile(ROOT_DIR + Constants.CONFIG
		} catch (IOException e) {
			throw new TransportException(uri
		}
	}

	private static String pickHEAD(List<RemoteRefUpdate> updates) {
		for (RemoteRefUpdate u : updates) {
			final String n = u.getRemoteName();
			if (n.equals(Constants.R_HEADS + Constants.MASTER))
				return n;
		}

		for (RemoteRefUpdate u : updates) {
			final String n = u.getRemoteName();
			if (n.startsWith(Constants.R_HEADS))
				return n;
		}
		return updates.get(0).getRemoteName();
	}
}
