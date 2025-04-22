package net.spy.memcached.tapmessage;

import java.nio.ByteBuffer;

import net.spy.memcached.compat.SpyObject;

public class BaseMessage extends SpyObject {
	public static final int MAGIC_INDEX = 0;

	public static final int MAGIC_FIELD_LENGTH = 1;

	public static final int OPCODE_INDEX = 1;

	public static final int OPCODE_FIELD_LENGTH = 1;

	public static final int KEY_LENGTH_INDEX = 2;

	public static final int KEY_LENGTH_FIELD_LENGTH = 2;

	public static final int EXTRA_LENGTH_INDEX = 4;

	public static final int EXTRA_LENGTH_FIELD_LENGTH = 1;

	public static final int DATA_TYPE_INDEX = 5;

	public static final int DATA_TYPE_FIELD_LENGTH = 1;

	public static final int VBUCKET_INDEX = 6;

	public static final int VBUCKET_FIELD_LENGTH = 2;

	public static final int TOTAL_BODY_INDEX = 8;

	public static final int TOTAL_BODY_FIELD_LENGTH = 4;

	public static final int OPAQUE_INDEX = 12;

	public static final int OPAQUE_FIELD_LENGTH = 4;

	public static final int CAS_INDEX = 16;

	public static final int CAS_FIELD_LENGTH = 8;

	public static final int HEADER_LENGTH = 24;


	protected byte[] mbytes;
	protected BaseMessage() {
		mbytes = new byte[HEADER_LENGTH];
	}

	public final void setMagic(TapMagic m) {
		mbytes[MAGIC_INDEX] = (byte) m.magic;
	}

	public final int getMagic() {
		return mbytes[MAGIC_INDEX];
	}

	public final void setOpcode(TapOpcode o) {
		mbytes[OPCODE_INDEX] = (byte) o.opcode;
	}

	public final TapOpcode getOpcode() {
		return TapOpcode.getOpcodeByByte(mbytes[OPCODE_INDEX]);
	}

	protected final void setKeylength(long l) {
		Util.valueToFieldOffest(mbytes, KEY_LENGTH_INDEX, KEY_LENGTH_FIELD_LENGTH, l);
	}

	public final int getKeylength() {
		return (int) Util.fieldToValue(mbytes, KEY_LENGTH_INDEX, KEY_LENGTH_FIELD_LENGTH);
	}

	public final void setDatatype(byte b) {
		mbytes[DATA_TYPE_INDEX] = b;
	}

	public final byte getDatatype() {
		return mbytes[DATA_TYPE_INDEX];
	}

	public final void setExtralength(int i) {
		mbytes[EXTRA_LENGTH_INDEX] = (byte) i;
	}

	public final int getExtralength() {
		return mbytes[EXTRA_LENGTH_INDEX];
	}

	public final void setVbucket(int vb) {
		Util.valueToFieldOffest(mbytes, VBUCKET_INDEX, VBUCKET_FIELD_LENGTH, vb);
	}

	public final int getVbucket() {
		return (int) Util.fieldToValue(mbytes, VBUCKET_INDEX, VBUCKET_FIELD_LENGTH);
	}

	public final void setTotalbody(long l) {
		Util.valueToFieldOffest(mbytes, TOTAL_BODY_INDEX, TOTAL_BODY_FIELD_LENGTH, l);
	}

	public final int getTotalbody() {
		return (int) Util.fieldToValue(mbytes, TOTAL_BODY_INDEX, TOTAL_BODY_FIELD_LENGTH);
	}

	public final void setOpaque(int op) {
		Util.valueToFieldOffest(mbytes, OPAQUE_INDEX, OPAQUE_FIELD_LENGTH, op);
	}

	public final int getOpaque() {
		return (int) Util.fieldToValue(mbytes, OPAQUE_INDEX, OPAQUE_FIELD_LENGTH);
	}

	public final void setCas(long cas) {
		Util.valueToFieldOffest(mbytes, CAS_INDEX, CAS_FIELD_LENGTH, cas);
	}

	public final long getCas() {
		return Util.fieldToValue(mbytes, CAS_INDEX, CAS_FIELD_LENGTH);
	}

	public final int getMessageLength() {
		return HEADER_LENGTH + getTotalbody();
	}

	public final ByteBuffer getBytes() {
		return ByteBuffer.wrap(mbytes);
	}
}
