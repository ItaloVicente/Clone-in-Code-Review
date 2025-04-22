
package org.eclipse.jgit.util.fs;

import java.util.Date;

public final class FileInfo {


	private int atime;

	private int ctime;

	private int mtime;

	private int atime_nsec;

	private int ctime_nsec;

	private int mtime_nsec;

	private int dev;

	private int ino;

	private int mode;

	private int uid;

	private int gid;

	private long size;

	FileInfo(long lastModified
		this.mtime = (int) (lastModified / 1000);
		this.mtime_nsec = ((int) (lastModified % 1000)) * 1000000;
		this.mode = mode;
		this.size = size;
	}

	public long length() {
		return size;
	}

	public int mode() {
		return mode;
	}

	public long lastModified() {
		return ts(mtime
	}

	public int lastModifiedSeconds() {
		return mtime;
	}

	public int lastModifiedNanos() {
		return mtime_nsec;
	}

	public long created() {
		return ts(ctime
	}

	public int createdSeconds() {
		return ctime;
	}

	public int createdNanos() {
		return ctime_nsec;
	}

	public long lastAccessed() {
		return ts(atime
	}

	public int lastAccessedSeconds() {
		return atime;
	}

	public int lastAccessedNanos() {
		return atime_nsec;
	}

	public int device() {
		return dev;
	}

	public int inode() {
		return ino;
	}

	public int uid() {
		return uid;
	}

	public int gid() {
		return gid;
	}

	private static long ts(long sec
		return (sec * 1000L) + (nsec / 1000000);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("length: ").append(length()).append("\n");
		s.append("mode: ").append(Integer.toOctalString(mode())).append("\n");
		s.append("lastModified: ").append(new Date(lastModified())).append("\n");
		s.append("created: ").append(new Date(created())).append("\n");
		s.append("lastAccessed: ").append(new Date(lastAccessed())).append("\n");
		s.append("device: ").append(device()).append("\n");
		s.append("inode: ").append(inode()).append("\n");
		s.append("uid/gid: ").append(uid()).append("/").append(gid());
		return s.toString();
	}
}
