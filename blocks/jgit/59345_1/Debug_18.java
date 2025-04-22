
package org.eclipse.jgit.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.lib.Repository;

public abstract class AbstractDotFileManager<T> {


	private final File m_root;
	private final FS m_fs;

	private final Map<String

	private final ReentrantReadWriteLock m_lockX = new ReentrantReadWriteLock(
			true);

	private final String m_dotFileName;

	private IFileLocator m_globalFileLocator;

	private IFileLocator m_infoFileLocator;


	public AbstractDotFileManager(File root
		m_root = root;
		m_fs = fs;
		m_dotFileName = dotFileName;
	}

	protected void setGlobalFileLocator(IFileLocator globalFileLocator) {
		m_globalFileLocator = globalFileLocator;
	}

	protected void setInfoFileLocator(IFileLocator infoFileLocator) {
		m_infoFileLocator = infoFileLocator;
	}

	public void clear() throws IOException {
		m_lockX.writeLock().lock();
		try {
			m_dotFiles.clear();
			clearSnapshots();
		} finally {
			m_lockX.writeLock().unlock();
		}
	}

	protected ReadWriteLock getLock() {
		return m_lockX;
	}

	protected T lookupGlobalNode() throws IOException {
		return readCachedNode(PATH_GLOBAL
	}

	protected T lookupInfoNode() throws IOException {
		return readCachedNode(PATH_INFO
	}

	protected T lookupRootNode() throws IOException {
	}

	protected T lookupWorkTreeNode(final String folderPath) throws IOException {
		return readCachedNode(folderPath
			@Override
			public File locateFile() throws IOException {
				if (m_root == null)
					return null;
				if (folderPath.isEmpty())
					return m_fs.resolve(m_root
				return m_fs.resolve(m_root
			}
		});
	}

	private T readCachedNode(String id
			throws IOException {
		VolatileDotFile<T> v;
		m_lockX.readLock().lock();
		try {
			v = m_dotFiles.get(id);
		} finally {
			m_lockX.readLock().unlock();
		}
		if (v != null && !v.isDirty()) {
			return v.getNode();
		}

		if (fileLocator == null) {
			return null;
		}

		v = writeCachedNode(id
		if (v == null) {
			return null;
		}

		return v.getNode();
	}

	private VolatileDotFile<T> writeCachedNode(String id
			IFileLocator fileLocator) throws IOException {
		m_lockX.writeLock().lock();
		try {
			m_dotFiles.remove(id);

			VolatileDotFile<T> v = null;
			File f = fileLocator.locateFile();
			if (f != null && m_fs.exists(f)) {
				v = new VolatileDotFile<>(loadNode(f)
				m_dotFiles.put(id
			}
			clearSnapshots();
			return v;
		} finally {
			m_lockX.writeLock().unlock();
		}
	}

	protected abstract T loadNode(File f) throws IOException;

	protected abstract void clearSnapshots();

	protected interface IFileLocator {
		File locateFile() throws IOException;
	}

	private static final class VolatileDotFile<T> {
		private final T m_node;

		private final File m_file;
		private final FileSnapshot m_snapshot;

		VolatileDotFile(T node
			m_node = node;
			m_file=file;
			m_snapshot = m_node != null ? FileSnapshot.save(file)
					: FileSnapshot.MISSING_FILE;
		}

		public T getNode() {
			return m_node;
		}

		public boolean isDirty() throws IOException {
			return m_snapshot.isModified(m_file);
		}
	}
}
