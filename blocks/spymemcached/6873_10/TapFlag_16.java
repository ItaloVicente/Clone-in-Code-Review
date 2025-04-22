package net.spy.memcached.tapmessage;

public class ResponseMessage extends BaseMessage{
	private static final int ENGINE_PRIVATE_OFFSET = 0;
	private static final int ENGINE_PRIVATE_FIELD_LENGTH = 2;
	private static final int FLAGS_OFFSET = 2;
	private static final int FLAGS_FIELD_LENGTH = 2;
	private static final int TTL_OFFSET = 3;
	private static final int TTL_FIELD_LENGTH = 1;
	private static final int RESERVED1_OFFSET = 4;
	private static final int RESERVED1_FIELD_LENGTH = 1;
	private static final int RESERVED2_OFFSET = 5;
	private static final int RESERVED2_FIELD_LENGTH = 1;
	private static final int RESERVED3_OFFSET = 6;
	private static final int RESERVED3_FIELD_LENGTH = 1;
	private static final int ITEM_FLAGS_OFFSET = 7;
	private static final int ITEM_FLAGS_FIELD_LENGTH = 4;
	private static final int ITEM_EXPIRY_OFFSET = 11;
	private static final int ITEM_EXPIRY_FIELD_LENGTH = 5;

	public ResponseMessage(byte[] buffer) {
		mbytes = buffer;
	}

	public long getEnginePrivate() {
		if (ENGINE_PRIVATE_OFFSET + ENGINE_PRIVATE_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + ENGINE_PRIVATE_OFFSET;
		return Util.fieldToValue(mbytes, offset, ENGINE_PRIVATE_FIELD_LENGTH);
	}

	public int getFlags() {
		if (FLAGS_OFFSET + FLAGS_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + FLAGS_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, FLAGS_FIELD_LENGTH);
	}

	public int getTTL() {
		if (TTL_OFFSET + TTL_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + TTL_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, TTL_FIELD_LENGTH);
	}

	public int getReserved1() {
		if (RESERVED1_OFFSET + RESERVED1_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED1_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED1_FIELD_LENGTH);
	}

	public int getReserved2() {
		if (RESERVED2_OFFSET + RESERVED2_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED2_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED2_FIELD_LENGTH);
	}

	public int getReserved3() {
		if (RESERVED3_OFFSET + RESERVED3_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + RESERVED3_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, RESERVED3_FIELD_LENGTH);
	}

	public int getItemFlags() {
		if (ITEM_FLAGS_OFFSET + ITEM_FLAGS_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + ITEM_FLAGS_OFFSET;
		return (int) Util.fieldToValue(mbytes, offset, ITEM_FLAGS_FIELD_LENGTH);
	}

	public long getItemExpiry() {
		if (ITEM_EXPIRY_OFFSET + ITEM_EXPIRY_FIELD_LENGTH > getExtralength()) {
			return 0;
		}
		int offset = HEADER_LENGTH + ITEM_EXPIRY_OFFSET;
		return Util.fieldToValue(mbytes, offset, ITEM_EXPIRY_FIELD_LENGTH);
	}

	public String getKey() {
		if (getExtralength() >= getTotalbody()) {
			return new String();
		}
		int offset = (int) (HEADER_LENGTH + getExtralength());
		return new String(mbytes, offset, getKeylength());
	}

	public byte[] getValue() {
		if (getExtralength() + getKeylength() >= getTotalbody()) {
			return new byte[0];
		}
		int offset = (int) (HEADER_LENGTH + getExtralength() + getKeylength());
		int length = (int) (getTotalbody() - getKeylength() - getExtralength());
		byte[] value = new byte[length];
		System.arraycopy(mbytes, offset, value, 0, length);
		return value;
	}
}
