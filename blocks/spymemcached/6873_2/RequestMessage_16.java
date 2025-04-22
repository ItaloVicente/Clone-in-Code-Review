package net.spy.memcached.tapmessage;

public enum Opcode {
	NOOP((byte) 0x0a),

	SASLLIST((byte) 0x20),

	SASLAUTH((byte) 0x21),

	REQUEST((byte) 0x40),

	MUTATION((byte) 0x41),

	DELETE((byte) 0x42),

	FLUSH((byte) 0x43),

	OPAQUE((byte)0x44),

	VBUCKETSET((byte) 0x45);

	public byte opcode;

	Opcode(byte opcode) {
		this.opcode = opcode;
	}

	public static Opcode getOpcodeByByte(byte b) {
		if (b == Opcode.DELETE.opcode) {
			return Opcode.DELETE;
		} else if (b == Opcode.FLUSH.opcode){
			return Opcode.DELETE;
		} else if (b == Opcode.MUTATION.opcode){
			return Opcode.MUTATION;
		} else if (b == Opcode.NOOP.opcode){
			return Opcode.NOOP;
		} else if (b == Opcode.OPAQUE.opcode){
			return Opcode.OPAQUE;
		} else if (b == Opcode.REQUEST.opcode){
			return Opcode.REQUEST;
		} else if (b == Opcode.SASLAUTH.opcode){
			return Opcode.SASLAUTH;
		} else if (b == Opcode.SASLLIST.opcode){
			return Opcode.SASLLIST;
		} else if (b == Opcode.VBUCKETSET.opcode){
			return Opcode.VBUCKETSET;
		} else {
			return null;
		}
	}
}
