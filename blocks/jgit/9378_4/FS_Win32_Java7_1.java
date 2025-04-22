
package org.eclipse.jgit.java7;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.internal.FS_POSIX;

public class FS_POSIX_Java7 extends FS_POSIX {

	FS_POSIX_Java7(FS_POSIX_Java7 src) {
		super(src);
	}

	FS_POSIX_Java7() {
	}

	@Override
	public FS newInstance() {
		return new FS_POSIX_Java7(this);
	}

	@Override
	public boolean supportsExecute() {
		return true;
	}

	@Override
	public boolean canExecute(File f) {
		return FileUtil.canExecute(f);
	}

	@Override
	public boolean setExecute(File f
		return FileUtil.setExecute(f
	}

	@Override
	public boolean retryFailedLockFileCommit() {
		return false;
	}

	@Override
	public boolean supportsSymlinks() {
		return true;
	}

	@Override
	public boolean isSymLink(File path) throws IOException {
		return FileUtil.isSymlink(path);
	}

	@Override
	public long lastModified(File path) throws IOException {
		return FileUtil.lastModified(path);
	}

	@Override
	public void setLastModified(File path
		FileUtil.setLastModified(path
	}

	@Override
	public long length(File f) throws IOException {
		return FileUtil.getLength(f);
	}

	@Override
	public boolean exists(File path) {
		return FileUtil.exists(path);
	}

	@Override
	public boolean isDirectory(File path) {
		return FileUtil.isDirectory(path);
	}

	@Override
	public boolean isFile(File path) {
		return FileUtil.isFile(path);
	}

	@Override
	public boolean isHidden(File path) throws IOException {
		return FileUtil.isHidden(path);
	}

	@Override
	public void setHidden(File path
		super.setHidden(path
	}

	@Override
	public String readSymLink(File path) throws IOException {
		return FileUtil.readSymlink(path);
	}

	@Override
	public void createSymLink(File path
		FileUtil.createSymLink(path
	}
}
