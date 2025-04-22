package org.eclipse.jgit.lfs.server;

public class LfsObject {
	String oid;
	long size;

	public String getOid() {
		return oid;
	}

	public long getSize() {
		return size;
	}
}
