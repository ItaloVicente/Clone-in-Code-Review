package net.spy.memcached.tapmessage;

public enum TapFlag {
	BACKFILL((byte) 0x01),

	DUMP((byte) 0x02),

	LIST_VBUCKETS((byte) 0x04),

	TAKEOVER_VBUCKETS((byte) 0x08),

	SUPPORT_ACK((byte) 0x10),

	KEYS_ONLY((byte) 0x20),

	CHECKPOINT((byte) 0x40);

	public byte flag;

	TapFlag(byte flag) {
		this.flag = flag;
	}

	boolean hasFlag(int f) {
		if ((f & (int) flag) == 1) {
			return false;
		}
		return true;
	}
}
