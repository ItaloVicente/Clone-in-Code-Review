package net.spy.memcached.tapmessage;

public enum TapMagic {
	PROTOCOL_BINARY_REQ((byte) 0x80),

	PROTOCOL_BINARY_RES((byte) 0x81);

	public byte magic;

	TapMagic(byte magic) {
		this.magic = magic;
	}
}
