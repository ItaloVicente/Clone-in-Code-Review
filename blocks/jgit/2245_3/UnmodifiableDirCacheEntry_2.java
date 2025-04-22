package org.eclipse.jgit.dircache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.MutableInteger;

public class UnmodifiableDirCache extends DirCache {

	public UnmodifiableDirCache(final File indexLocation
			throws CorruptObjectException
		super(indexLocation
		super.read();
		super.getCacheTree(true);
	}

	@Override
	protected DirCacheEntry createDirCacheEntry(BufferedInputStream in
			MessageDigest md
			throws IOException {
		return new UnmodifiableDirCacheEntry(infos
	}

	@Override
	public DirCacheBuilder builder() {
		throw new UnsupportedOperationException();
	}

	@Override
	public DirCacheEditor editor() {
		throw new UnsupportedOperationException();
	}

	@Override
	void replace(DirCacheEntry[] e
		throw new UnsupportedOperationException();
	}

	@Override
	public void read() throws IOException
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean lock() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	void writeTo(OutputStream os) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean commit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void unlock() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectId writeTree(ObjectInserter ow) throws UnmergedPathException
			IOException {
		throw new UnsupportedOperationException();
	}

}
