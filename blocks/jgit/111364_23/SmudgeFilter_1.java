package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.lfs.internal.LfsConnectionFactory;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class LfsPrePushHook extends PrePushHook {

	private Collection<RemoteRefUpdate> refs;

	public LfsPrePushHook(Repository repo
		super(repo
	}

	@Override
	public void setRefs(Collection<RemoteRefUpdate> toRefs) {
		this.refs = toRefs;
	}

	@Override
	public String call() throws IOException
		Set<LfsPointer> toPush = findObjectsToPush();

		if (toPush.isEmpty()) {
		}

		Lfs lfs = new Lfs(getRepository());

		LfsPointer[] res = toPush.toArray(new LfsPointer[toPush.size()]);
		Map<String
		for (LfsPointer p : res) {
			oidStr2ptr.put(p.getOid().name()
		}
		HttpConnection lfsServerConn = LfsConnectionFactory.getLfsConnection(
				getRepository()
				Protocol.OPERATION_UPLOAD);
		Gson gson = new Gson();
		lfsServerConn.getOutputStream()
				.write(gson
						.toJson(LfsConnectionFactory
								.toRequest(Protocol.OPERATION_UPLOAD
						.getBytes(StandardCharsets.UTF_8));
		int responseCode = lfsServerConn.getResponseCode();
		if (responseCode != HttpConnection.HTTP_OK) {
			throw new IOException(MessageFormat.format(
					LfsText.get().serverFailure
					Integer.valueOf(responseCode)));
		}

		try (JsonReader reader = new JsonReader(
				new InputStreamReader(lfsServerConn.getInputStream()))) {
			Protocol.Response resp = gson.fromJson(reader
					Protocol.Response.class);
			for (Protocol.ObjectInfo o : resp.objects) {
				if (o.actions == null) {
					continue;
				}
				LfsPointer ptr = oidStr2ptr.get(o.oid);
				if (ptr == null) {
					continue;
				}
				Protocol.Action uploadAction = o.actions
						.get(Protocol.OPERATION_UPLOAD);
				if (uploadAction == null || uploadAction.href == null) {
					continue;
				}

				HttpConnection contentServerConn = LfsConnectionFactory
						.getLfsContentConnection(getRepository()
								HttpSupport.METHOD_PUT);
				contentServerConn.setDoOutput(true);

				Path path = lfs.getMediaFile(ptr.getOid());
				if (!Files.exists(path)) {
					throw new IOException(MessageFormat
							.format(LfsText.get().missingLocalObject
				}
				try (OutputStream contentOut = contentServerConn
						.getOutputStream()) {
					long bytesCopied = Files.copy(path
					if (bytesCopied != o.size) {
						throw new IOException(MessageFormat.format(
								LfsText.get().wrongAmoutOfDataWritten
								contentServerConn.getURL()
								Long.valueOf(bytesCopied)
								Long.valueOf(o.size)));
					}
				}
				responseCode = contentServerConn.getResponseCode();
				if (responseCode != HttpConnection.HTTP_OK) {
					throw new IOException(
							MessageFormat.format(LfsText.get().serverFailure
									contentServerConn.getURL()
									Integer.valueOf(responseCode)));
				}
			}
		}
	}

	private Set<LfsPointer> findObjectsToPush() throws IOException
			MissingObjectException
		Set<LfsPointer> toPush = new TreeSet<>();

		for (RemoteRefUpdate up : refs) {
			try (ObjectWalk walk = new ObjectWalk(getRepository())) {
				walk.setRewriteParents(false);

				excludeAllRemoteRefs(walk);

				walk.markStart(walk.parseCommit(up.getNewObjectId()));
				for (;;) {
					if (walk.next() == null) {
						break;
					}
				}

				findAllLfsPointers(toPush
			}
		}
		return toPush;
	}

	private void findAllLfsPointers(Set<LfsPointer> toPush
			throws MissingObjectException
			IOException {
		for (;;) {
			final RevObject obj = walk.nextObject();
			if (obj == null) {
				break;
			}

			if (obj.getType() == Constants.OBJ_BLOB) {
				long objectSize = walk.getObjectReader()
						.getObjectSize(obj.getId()
				if (objectSize > LfsPointer.SIZE_THRESHOLD) {
					continue;
				} else {
					try (InputStream is = walk.getObjectReader()
							.open(obj.getId()
							.openStream()) {
						LfsPointer ptr = LfsPointer.parseLfsPointer(is);
						if (ptr != null) {
							toPush.add(ptr);
						}
					}
				}
			}
		}
	}

	private void excludeAllRemoteRefs(ObjectWalk walk)
			throws IOException
		Map<String
				.getRefs(Constants.R_REMOTES + (getRemoteName() == null
						? Constants.DEFAULT_REMOTE_NAME
						: getRemoteName()));
		for (Ref r : rr.values()) {
			ObjectId oid = r.getPeeledObjectId();
			if (oid == null) {
				oid = r.getObjectId();
			}
			RevObject o = walk.parseAny(oid);
			if (o.getType() == Constants.OBJ_COMMIT
					|| o.getType() == Constants.OBJ_TAG) {
				walk.markUninteresting(o);
			}
		}
	}

}
