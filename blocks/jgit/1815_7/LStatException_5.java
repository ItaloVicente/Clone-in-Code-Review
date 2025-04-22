package org.eclipse.jgit.util.fs;

import org.eclipse.jgit.lib.FileMode;

public final class LStat {
	private int ctimeSec;

	private int ctimeNSec;

	private int mtimeSec;

	private int mtimeNSec;

	private int device;

	private int inode;

	private FileMode mode;

	private int userId;

	private int groupId;

	private long size;

	LStat(long lastModified
		mtimeSec = (int) (lastModified / 1000);
		mtimeNSec = (int) (lastModified % 1000) * 1000000;
		this.mode = mode;
		this.size = size;
	}

	LStat(int[] rawlstat) {
		ctimeSec = rawlstat[0];
		ctimeNSec = rawlstat[1];
		mtimeSec = rawlstat[2];
		mtimeNSec = rawlstat[3];
		device = rawlstat[4];
		inode = rawlstat[5];
		mode = FileMode.fromBits(rawlstat[6]);
		userId = rawlstat[7];
		groupId = rawlstat[8];
		size = (((long) rawlstat[9]) << 32) + rawlstat[10];
	}

	public int getCTimeSec() {
		return ctimeSec;
	}

	public int getCTimeNSec() {
		return ctimeNSec;
	}

	public int getMTimeSec() {
		return mtimeSec;
	}

	public int getMTimeNSec() {
		return mtimeNSec;
	}

	public int getDevice() {
		return device;
	}

	public int getInode() {
		return inode;
	}

	public FileMode getMode() {
		return mode;
	}

	public int getUserId() {
		return userId;
	}

	public int getGroupId() {
		return groupId;
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
