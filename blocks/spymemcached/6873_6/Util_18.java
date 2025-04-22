package net.spy.memcached.tapmessage;

public enum TapOpcode {
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

	TapOpcode(byte opcode) {
		this.opcode = opcode;
	}

	public static TapOpcode getOpcodeByByte(byte b) {
		if (b == TapOpcode.DELETE.opcode) {
			return TapOpcode.DELETE;
		} else if (b == TapOpcode.FLUSH.opcode){
			return TapOpcode.DELETE;
		} else if (b == TapOpcode.MUTATION.opcode){
			return TapOpcode.MUTATION;
		} else if (b == TapOpcode.NOOP.opcode){
			return TapOpcode.NOOP;
		} else if (b == TapOpcode.OPAQUE.opcode){
			return TapOpcode.OPAQUE;
		} else if (b == TapOpcode.REQUEST.opcode){
			return TapOpcode.REQUEST;
		} else if (b == TapOpcode.SASLAUTH.opcode){
			return TapOpcode.SASLAUTH;
		} else if (b == TapOpcode.SASLLIST.opcode){
			return TapOpcode.SASLLIST;
		} else if (b == TapOpcode.VBUCKETSET.opcode){
			return TapOpcode.VBUCKETSET;
		} else {
			return null;
		}
	}
}
