package org.eclipse.jgit.dircache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.util.MutableInteger;

public class UnmodifiableDirCacheEntry extends DirCacheEntry {

	public UnmodifiableDirCacheEntry(final byte[] sharedInfo
			final MutableInteger infoAt
			final MessageDigest md) throws IOException {
		super(sharedInfo
	}

	@Override
	public void copyMetaData(DirCacheEntry src) {
		throw new UnsupportedOperationException();
	}

	@Override
	void write(OutputStream os) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAssumeValid(boolean assume) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUpdateNeeded(boolean updateNeeded) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFileMode(FileMode mode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLastModified(long when) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLength(int sz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLength(long sz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectId(AnyObjectId id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectIdFromRaw(byte[] bs
		throw new UnsupportedOperationException();
	}

}
