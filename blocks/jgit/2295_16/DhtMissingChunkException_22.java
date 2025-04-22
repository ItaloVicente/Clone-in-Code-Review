
package org.eclipse.jgit.storage.dht;

import static java.util.zip.Deflater.DEFAULT_COMPRESSION;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.security.SecureRandom;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

public class DhtInserterOptions {
	private static final SecureRandom prng = new SecureRandom();

	public static final int KiB = 1024;

	public static final int MiB = 1024 * KiB;

	private int chunkSize;

	private int writeBufferSize;

	private int compression;

	private int prefetchDepth;

	private long parserCacheLimit;

	public DhtInserterOptions() {
		setChunkSize(1 * MiB);
		setWriteBufferSize(1 * MiB);
		setCompression(DEFAULT_COMPRESSION);
		setPrefetchDepth(50);
		setParserCacheLimit(512 * getChunkSize());
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public DhtInserterOptions setChunkSize(int sizeInBytes) {
		chunkSize = Math.max(1024
		return this;
	}

	public int getWriteBufferSize() {
		return writeBufferSize;
	}

	public DhtInserterOptions setWriteBufferSize(int sizeInBytes) {
		writeBufferSize = Math.max(1024
		return this;
	}

	public int getMaxObjectCount() {
		return getChunkSize() / (OBJECT_ID_LENGTH + 4);
	}

	public int getCompression() {
		return compression;
	}

	public DhtInserterOptions setCompression(int level) {
		compression = level;
		return this;
	}

	public int getPrefetchDepth() {
		return prefetchDepth;
	}

	public DhtInserterOptions setPrefetchDepth(int depth) {
		prefetchDepth = Math.max(0
		return this;
	}

	public int getParserCacheSize() {
		return (int) (getParserCacheLimit() / getChunkSize());
	}

	public long getParserCacheLimit() {
		return parserCacheLimit;
	}

	public DhtInserterOptions setParserCacheLimit(long limit) {
		parserCacheLimit = Math.max(0
		return this;
	}

	int nextChunkSalt() {
		return prng.nextInt();
	}

	public DhtInserterOptions fromConfig(Config rc) {
		setChunkSize(rc.getInt("core"
		setWriteBufferSize(rc.getInt("core"
		setCompression(rc.get(CoreConfig.KEY).getCompression());
		setPrefetchDepth(rc.getInt("core"
		setParserCacheLimit(rc.getLong("core"
		return this;
	}
}
