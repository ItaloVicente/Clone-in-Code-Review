package org.eclipse.jgit.lfs;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lfs.Protocol.OPERATION_UPLOAD;
import static org.eclipse.jgit.lfs.internal.LfsConnectionFactory.toRequest;
import static org.eclipse.jgit.transport.http.HttpConnection.HTTP_OK;
import static org.eclipse.jgit.util.HttpSupport.METHOD_POST;
import static org.eclipse.jgit.util.HttpSupport.METHOD_PUT;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.lfs.Protocol.ObjectInfo;
import org.eclipse.jgit.lfs.errors.CorruptMediaFile;
import org.eclipse.jgit.lfs.internal.LfsConnectionFactory;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.http.HttpConnection;

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
			return EMPTY;
		}
		HttpConnection api = LfsConnectionFactory.getLfsConnection(
				getRepository()
		Map<String
		uploadContents(api
		return EMPTY;

	}

	private Set<LfsPointer> findObjectsToPush() throws IOException
			MissingObjectException
		Set<LfsPointer> toPush = new TreeSet<>();

		try (ObjectWalk walk = new ObjectWalk(getRepository())) {
			for (RemoteRefUpdate up : refs) {
				walk.setRewriteParents(false);
				excludeRemoteRefs(walk);
				walk.markStart(walk.parseCommit(up.getNewObjectId()));
				while (walk.next() != null) {
				}
				findLfsPointers(toPush
			}
		}
		return toPush;
	}

	private static void findLfsPointers(Set<LfsPointer> toPush
			throws MissingObjectException
			IOException {
		RevObject obj;
		ObjectReader r = walk.getObjectReader();
		while ((obj = walk.nextObject()) != null) {
			if (obj.getType() == Constants.OBJ_BLOB
					&& getObjectSize(r
				LfsPointer ptr = loadLfsPointer(r
				if (ptr != null) {
					toPush.add(ptr);
				}
			}
		}
	}

	private static long getObjectSize(ObjectReader r
			throws IOException {
		return r.getObjectSize(obj.getId()
	}

	private static LfsPointer loadLfsPointer(ObjectReader r
			throws IOException {
		try (InputStream is = r.open(obj
			return LfsPointer.parseLfsPointer(is);
		}
	}

	private void excludeRemoteRefs(ObjectWalk walk) throws IOException {
		RefDatabase refDatabase = getRepository().getRefDatabase();
		List<Ref> remoteRefs = refDatabase.getRefsByPrefix(remote());
		for (Ref r : remoteRefs) {
			ObjectId oid = r.getPeeledObjectId();
			if (oid == null) {
				oid = r.getObjectId();
			}
			if (oid == null) {
				continue;
			}
			RevObject o = walk.parseAny(oid);
			if (o.getType() == Constants.OBJ_COMMIT
					|| o.getType() == Constants.OBJ_TAG) {
				walk.markUninteresting(o);
			}
		}
	}

	private String remote() {
		String remoteName = getRemoteName() == null
				? Constants.DEFAULT_REMOTE_NAME
				: getRemoteName();
		return Constants.R_REMOTES + remoteName;
	}

	private Map<String
			Set<LfsPointer> toPush) throws IOException {
		LfsPointer[] res = toPush.toArray(new LfsPointer[0]);
		Map<String
		for (LfsPointer p : res) {
			oidStr2ptr.put(p.getOid().name()
		}
		Gson gson = Protocol.gson();
		api.getOutputStream().write(
				gson.toJson(toRequest(OPERATION_UPLOAD
		int responseCode = api.getResponseCode();
		if (responseCode != HTTP_OK) {
			throw new IOException(
					MessageFormat.format(LfsText.get().serverFailure
							api.getURL()
		}
		return oidStr2ptr;
	}

	private void uploadContents(HttpConnection api
			Map<String
		try (JsonReader reader = new JsonReader(
				new InputStreamReader(api.getInputStream()
			for (Protocol.ObjectInfo o : parseObjects(reader)) {
				if (o.actions == null) {
					continue;
				}
				LfsPointer ptr = oid2ptr.get(o.oid);
				if (ptr == null) {
					continue;
				}
				Protocol.Action uploadAction = o.actions.get(OPERATION_UPLOAD);
				if (uploadAction == null || uploadAction.href == null) {
					continue;
				}

				Lfs lfs = new Lfs(getRepository());
				Path path = lfs.getMediaFile(ptr.getOid());
				if (!Files.exists(path)) {
					throw new IOException(MessageFormat
							.format(LfsText.get().missingLocalObject
				}
				uploadFile(o
			}
		}
	}

	private List<ObjectInfo> parseObjects(JsonReader reader) {
		Gson gson = new Gson();
		Protocol.Response resp = gson.fromJson(reader
		return resp.objects;
	}

	private void uploadFile(Protocol.ObjectInfo o
			Protocol.Action uploadAction
			throws IOException
		HttpConnection contentServer = LfsConnectionFactory
				.getLfsContentConnection(getRepository()
						METHOD_PUT);
		contentServer.setDoOutput(true);
		try (OutputStream out = contentServer
				.getOutputStream()) {
			long size = Files.copy(path
			if (size != o.size) {
				throw new CorruptMediaFile(path
			}
		}
		int responseCode = contentServer.getResponseCode();
		if (responseCode != HTTP_OK) {
			throw new IOException(MessageFormat.format(
					LfsText.get().serverFailure
					Integer.valueOf(responseCode)));
		}
	}
}
