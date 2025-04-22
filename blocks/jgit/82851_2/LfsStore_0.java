package org.eclipse.jgit.lfs.server.fs;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;

public abstract class FileLfsTransferDescriptor {
	public TransferData getTransferData(HttpServletRequest req)
			throws IllegalArgumentException {
		String info = req.getPathInfo();
		FileLfsRepository repository = getRepositoryFromPath(info);
		AnyLongObjectId id = getObjectFromPath(info);
		return new TransferData(id
	}

	protected AnyLongObjectId getObjectFromPath(String path)
			throws IllegalArgumentException {
		if (path.length() != 1 + Constants.LONG_OBJECT_ID_STRING_LENGTH) {
			throw new IllegalArgumentException(MessageFormat
					.format(LfsServerText.get().invalidPathInfo
		}

		return LongObjectId.fromString(
				path.substring(1
	}

	abstract protected FileLfsRepository getRepositoryFromPath(String path)
			throws IllegalArgumentException;

	public static class DefaultFileLfsTransferDescriptor
			extends FileLfsTransferDescriptor {
		private final FileLfsRepository repository;

		public DefaultFileLfsTransferDescriptor(FileLfsRepository repository) {
			this.repository = repository;
		}

		@Override
		protected FileLfsRepository getRepositoryFromPath(String path)
				throws IllegalArgumentException {
			return repository;
		}
	}

	public static class TransferData {
		public final AnyLongObjectId obj;

		public final FileLfsRepository repository;

		TransferData(AnyLongObjectId obj
				FileLfsRepository repository) {
			this.obj = obj;
			this.repository = repository;
		}
	}
}
