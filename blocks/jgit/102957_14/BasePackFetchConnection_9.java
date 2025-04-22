
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryShallow;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;

public class FileBasedShallow extends RepositoryShallow {

	private final File shallowFile;
	private final LockFile shallowLockFile;

	private final List<ObjectId> commitIds;

	public FileBasedShallow(final File parentDirectory) {
		this.commitIds = new ArrayList<>();
		if (parentDirectory == null) {
			throw new RuntimeException(
					JGitText.get().repositoryLocalMetadataDirectoryInvalid);
		}
		shallowFile = new File(parentDirectory
		shallowLockFile = new LockFile(shallowFile);
	}

	@Override
	public void lock() throws IOException {
		if (!shallowLockFile.lock()) {
			final String path = shallowFile.getAbsolutePath();
			throw new RepositoryShallowException(JGitText.get().cannotLock
					path);
		}
	}

	@Override
	public List<ObjectId> read() throws IOException {
		commitIds.clear();
		if (!shallowFile.exists()) {
			return Collections.emptyList();
		}
		final FileReader in = new FileReader(shallowFile);
		try {
			for (String line;;) {
				line = IO.readLine(in
				if (line.length() == 0) {
					break;
				}
				final ObjectId id = convertStringToObjectId(line
						HASH_LENGTH);
				commitIds.add(id);
			}
		} finally {
			in.close();
		}
		return Collections.unmodifiableList(commitIds);
	}

	@Override
	public boolean parseShallowUnshallowLine(final String line)
			throws IOException {
		final int length = line.length();
		if (length == 0) {
			return false;
		}
		if (line.startsWith(PREFIX_SHALLOW)) {
			final ObjectId objId = convertStringToObjectId(line
					PREFIX_SHALLOW.length()
			commitIds.add(objId);
		} else if (line.startsWith(PREFIX_UNSHALLOW)) {
			final ObjectId objId = convertStringToObjectId(line
					PREFIX_UNSHALLOW.length()
			commitIds.remove(objId);
		} else {
			throw new RepositoryShallowException(
					JGitText.get().expectedShallowUnshallowGot
		}
		return true;
	}

	@Override
	public void unlock(final boolean writeChanges) throws IOException {
		if (writeChanges) {
			try {
				writeChanges();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				shallowLockFile.unlock();
			}
		} else {
			shallowLockFile.unlock();
		}
	}

	private void writeChanges() throws IOException {
		if (commitIds.isEmpty()) {
			if (shallowFile.exists()) {
				FileUtils.delete(shallowFile);
				shallowLockFile.unlock();
			}
			return;
		}
		final StringBuffer buffer = new StringBuffer();
		Collections.sort(commitIds);
		for (ObjectId id : commitIds) {
			buffer.append(id.name());
			buffer.append('\n');
		}
		final String contentAsString = buffer.toString();
		final byte[] content = contentAsString.getBytes();
		shallowLockFile.write(content);
		shallowLockFile.commit();
	}

}
