package org.eclipse.jgit.util.fs;

import org.eclipse.jgit.lib.FileMode;

public final class LStat {

	private int ctime;

	private int mtime;

	private int ctime_nsec;

	private int mtime_nsec;

	private int dev;

	private int ino;

	private int mode;

	private int uid;

	private int gid;

	private long size;

	LStat(long lastModified
		mtime = (int) (lastModified / 1000);
		mtime_nsec = (int) (lastModified % 1000) * 1000000;
		this.mode = mode.getBits();
		this.size = size;
	}

	public int getCTimeSec() {
		return ctime;
	}

	public int getCTimeNSec() {
		return ctime_nsec;
	}

	public int getMTimeSec() {
		return mtime;
	}

	public int getMTimeNSec() {
		return mtime_nsec;
	}

	public int getDevice() {
		return dev;
	}

	public int getInode() {
		return ino;
	}

	public FileMode getMode() {
		return FileMode.fromBits(mode);
	}

	public int getUserId() {
		return uid;
	}

	public int getGroupId() {
		return gid;
	}

	public long getSize() {
		return size;
	}

	public String toString() {
		StringBuilder s = new StringBuilder("ctime: ");
		s.append(getCTimeSec());
		s.append("\nctimensec: ").append(getCTimeNSec());
		s.append("\nmtime: ").append(getMTimeSec());
		s.append("\nmtimensec: ").append(getMTimeNSec());
		s.append("\ndevice: ").append(getDevice());
		s.append("\ninode: ").append(getInode());
		s.append("\nmode: ").append(getMode());
		s.append("\nuid: ").append(getUserId());
		s.append("\ngid: ").append(getGroupId());
		s.append("\nsize: ").append(getSize());
		return s.toString();
	}
}
