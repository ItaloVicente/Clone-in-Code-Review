package org.eclipse.jgit.lfs.server.fs;

import static org.eclipse.jgit.util.HttpSupport.HDR_AUTHORIZATION;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.server.LargeFileRepository;
import org.eclipse.jgit.lfs.server.Response;
import org.eclipse.jgit.lfs.server.Response.Action;

public class FileLfsRepository implements LargeFileRepository {

	private final String url;
	private final Path dir;
	private AtomicObjectOutputStream out;

	public FileLfsRepository(String url
		this.url = url;
		this.dir = dir;
		Files.createDirectories(dir);
	}

	@Override
	public Response.Action getDownloadAction(AnyLongObjectId id) {
		return getAction(id);
	}

	@Override
	public Action getUploadAction(AnyLongObjectId id
		return getAction(id);
	}

	@Override
	public @Nullable Action getVerifyAction(AnyLongObjectId id) {
		return null;
	}

	@Override
	public long getSize(AnyLongObjectId id) throws IOException {
		Path p = getPath(id);
		if (Files.exists(p)) {
			return Files.size(p);
		} else {
			return -1;
		}
	}

	public Path getDir() {
		return dir;
	}

	protected Path getPath(AnyLongObjectId id) {
		StringBuilder s = new StringBuilder(
				Constants.LONG_OBJECT_ID_STRING_LENGTH + 6);
		s.append(toHexCharArray(id.getFirstByte())).append('/');
		s.append(toHexCharArray(id.getSecondByte())).append('/');
		s.append(id.name());
		return dir.resolve(s.toString());
	}

	private Response.Action getAction(AnyLongObjectId id) {
		Response.Action a = new Response.Action();
		a.href = url + id.getName();
		a.header = Collections.singletonMap(HDR_AUTHORIZATION
		return a;
	}

	ReadableByteChannel getReadChannel(AnyLongObjectId id)
			throws IOException {
		return FileChannel.open(getPath(id)
	}

	WritableByteChannel getWriteChannel(AnyLongObjectId id)
			throws IOException {
		Path path = getPath(id);
		Files.createDirectories(path.getParent());
		out = new AtomicObjectOutputStream(path
		return Channels.newChannel(out);
	}

	void abortWrite() {
		if (out != null) {
			out.abort();
		}
	}

	private static char[] toHexCharArray(int b) {
		final char[] dst = new char[2];
		formatHexChar(dst
		return dst;
	}

	private static final char[] hexchar = { '0'
			'7'

	private static void formatHexChar(final char[] dst
		int o = p + 1;
		while (o >= p && b != 0) {
			dst[o--] = hexchar[b & 0xf];
			b >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}
}
