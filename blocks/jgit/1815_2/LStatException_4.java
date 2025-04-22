package org.eclipse.jgit.util.fs;

public final class LStat {
	private int[] rawlstat;

	LStat(long lastModified
		rawlstat = new int[10];
		rawlstat[2] = (int) (lastModified / 1000);
		rawlstat[3] = (int) (lastModified % 1000) * 1000000;
		rawlstat[9] = size;

	}

	LStat(int[] rawlstat) {
		this.rawlstat = rawlstat;
	}

	public int getCTimeSec() {
		return rawlstat[0];
	}

	public int getCTimeNSec() {
		return rawlstat[1];
	}

	public int getMTimeSec() {
		return rawlstat[2];
	}

	public int getMTimeNSec() {
		return rawlstat[3];
	}

	public int getDevice() {
		return rawlstat[4];
	}

	public int getInode() {
		return rawlstat[5];
	}

	public int getMode() {
		return rawlstat[6];
	}

	public int getUserId() {
		return rawlstat[7];
	}

	public int getGroupId() {
		return rawlstat[8];
	}

	public int getSize() {
		return rawlstat[9];
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
