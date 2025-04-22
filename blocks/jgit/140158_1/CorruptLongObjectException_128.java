package org.eclipse.jgit.lfs;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.lfs.internal.LfsConnectionFactory;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class SmudgeFilter extends FilterCommand {

	private static final int MAX_COPY_BYTES = 1024 * 1024 * 256;

	public final static FilterCommandFactory FACTORY = new FilterCommandFactory() {
		@Override
		public FilterCommand create(Repository db
				OutputStream out) throws IOException {
			return new SmudgeFilter(db
		}
	};

	static void register() {
		FilterCommandRegistry
				.register(org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
						+ Constants.ATTR_FILTER_DRIVER_PREFIX
						+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_SMUDGE
						FACTORY);
	}

	public SmudgeFilter(Repository db
			throws IOException {
		super(in
		try {
			Lfs lfs = new Lfs(db);
			LfsPointer res = LfsPointer.parseLfsPointer(in);
			if (res != null) {
				AnyLongObjectId oid = res.getOid();
				Path mediaFile = lfs.getMediaFile(oid);
				if (!Files.exists(mediaFile)) {
					downloadLfsResource(lfs
				}
				this.in = Files.newInputStream(mediaFile);
			}
		} finally {
		}
	}

	public static Collection<Path> downloadLfsResource(Lfs lfs
			LfsPointer... res) throws IOException {
		Collection<Path> downloadedPaths = new ArrayList<>();
		Map<String
		for (LfsPointer p : res) {
			oidStr2ptr.put(p.getOid().name()
		}
		HttpConnection lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST
		Gson gson = Protocol.gson();
		lfsServerConn.getOutputStream()
				.write(gson
						.toJson(LfsConnectionFactory
								.toRequest(Protocol.OPERATION_DOWNLOAD
						.getBytes(UTF_8));
		int responseCode = lfsServerConn.getResponseCode();
		if (responseCode != HttpConnection.HTTP_OK) {
			throw new IOException(
					MessageFormat.format(LfsText.get().serverFailure
							lfsServerConn.getURL()
							Integer.valueOf(responseCode)));
		}
		try (JsonReader reader = new JsonReader(
				new InputStreamReader(lfsServerConn.getInputStream()
						UTF_8))) {
			Protocol.Response resp = gson.fromJson(reader
					Protocol.Response.class);
			for (Protocol.ObjectInfo o : resp.objects) {
				if (o.error != null) {
					throw new IOException(
							MessageFormat.format(LfsText.get().protocolError
									Integer.valueOf(o.error.code)
									o.error.message));
				}
				if (o.actions == null) {
					continue;
				}
				LfsPointer ptr = oidStr2ptr.get(o.oid);
				if (ptr == null) {
					continue;
				}
				if (ptr.getSize() != o.size) {
					throw new IOException(MessageFormat.format(
							LfsText.get().inconsistentContentLength
							lfsServerConn.getURL()
							Long.valueOf(o.size)));
				}
				Protocol.Action downloadAction = o.actions
						.get(Protocol.OPERATION_DOWNLOAD);
				if (downloadAction == null || downloadAction.href == null) {
					continue;
				}

				HttpConnection contentServerConn = LfsConnectionFactory
						.getLfsContentConnection(db
								HttpSupport.METHOD_GET);

				responseCode = contentServerConn.getResponseCode();
				if (responseCode != HttpConnection.HTTP_OK) {
					throw new IOException(
							MessageFormat.format(LfsText.get().serverFailure
									contentServerConn.getURL()
									Integer.valueOf(responseCode)));
				}
				Path path = lfs.getMediaFile(ptr.getOid());
				Path parent = path.getParent();
				if (parent != null) {
					parent.toFile().mkdirs();
				}
				try (InputStream contentIn = contentServerConn
						.getInputStream()) {
					long bytesCopied = Files.copy(contentIn
					if (bytesCopied != o.size) {
						throw new IOException(MessageFormat.format(
								LfsText.get().wrongAmoutOfDataReceived
								contentServerConn.getURL()
								Long.valueOf(bytesCopied)
								Long.valueOf(o.size)));
					}
					downloadedPaths.add(path);
				}
			}
		}
		return downloadedPaths;
	}

	@Override
	public int run() throws IOException {
		try {
			int totalRead = 0;
			int length = 0;
			if (in != null) {
				byte[] buf = new byte[8192];
				while ((length = in.read(buf)) != -1) {
					out.write(buf
					totalRead += length;

					if (totalRead >= MAX_COPY_BYTES) {
						return totalRead;
					}
				}
			}

			if (totalRead == 0 && length == -1) {
				in.close();
				out.close();
				return length;
			}

			return totalRead;
		} catch (IOException e) {
			out.close();
			throw e;
		}
	}

}
