
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.RawParseUtils;

public class TinyProtobuf {
	private static final int WIRE_VARINT = 0;

	private static final int WIRE_FIXED_64 = 1;

	private static final int WIRE_LEN_DELIM = 2;

	private static final int WIRE_FIXED_32 = 5;

	public static Encoder encode(int estimatedSize) {
		return new Encoder(new byte[estimatedSize]);
	}

	public static Encoder size() {
		return new Encoder(null);
	}

	public static Decoder decode(byte[] buf) {
		return decode(buf
	}

	public static Decoder decode(byte[] buf
		return new Decoder(buf
	}

	public static interface Enum {
		public int value();
	}

	public static class Decoder {
		private final byte[] buf;

		private final int end;

		private int ptr;

		private int field;

		private int type;

		private Decoder(byte[] buf
			this.buf = buf;
			this.ptr = off;
			this.end = off + len;
		}

		public int next() {
			if (ptr == end)
				return 0;

			int fieldAndType = varint();
			field = fieldAndType >>> 3;
			type = fieldAndType & 7;
			return field;
		}

		public void skip() {
			switch (type) {
			case WIRE_VARINT:
				varint();
				break;
			case WIRE_FIXED_64:
				ptr += 8;
				break;
			case WIRE_LEN_DELIM:
				ptr += varint();
				break;
			case WIRE_FIXED_32:
				ptr += 4;
				break;
			default:
				throw new IllegalStateException(MessageFormat.format(DhtText
						.get().protobufUnsupportedFieldType
						.valueOf(type)));
			}
		}

		public int int32() {
			checkFieldType(WIRE_VARINT);
			return varint();
		}

		public <T extends Enum> T intEnum(T[] all) {
			checkFieldType(WIRE_VARINT);
			int value = varint();
			for (T t : all) {
				if (t.value() == value)
					return t;
			}
			throw new IllegalStateException(MessageFormat.format(
					DhtText.get().protobufWrongFieldType
							.valueOf(field)
							.getClass().getSimpleName()));
		}

		public boolean bool() {
			checkFieldType(WIRE_VARINT);
			int val = varint();
			switch (val) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException(MessageFormat.format(DhtText
						.get().protobufNotBooleanValue
						Integer.valueOf(val)));
			}
		}

		public long fixed64() {
			checkFieldType(WIRE_FIXED_64);
			long val = buf[ptr + 0] & 0xff;
			val |= ((long) (buf[ptr + 1] & 0xff)) << (1 * 8);
			val |= ((long) (buf[ptr + 2] & 0xff)) << (2 * 8);
			val |= ((long) (buf[ptr + 3] & 0xff)) << (3 * 8);
			val |= ((long) (buf[ptr + 4] & 0xff)) << (4 * 8);
			val |= ((long) (buf[ptr + 5] & 0xff)) << (5 * 8);
			val |= ((long) (buf[ptr + 6] & 0xff)) << (6 * 8);
			val |= ((long) (buf[ptr + 7] & 0xff)) << (7 * 8);
			ptr += 8;
			return val;
		}

		public String string() {
			checkFieldType(WIRE_LEN_DELIM);
			int len = varint();
			String s = RawParseUtils.decode(buf
			ptr += len;
			return s;
		}

		public ObjectId stringObjectId() {
			checkFieldType(WIRE_LEN_DELIM);
			int len = varint();
			if (len != OBJECT_ID_STRING_LENGTH)
				throw new IllegalStateException(MessageFormat.format(DhtText
						.get().protobufWrongFieldLength
						Integer.valueOf(field)
								.valueOf(OBJECT_ID_STRING_LENGTH)
								.valueOf(len)));

			ObjectId id = ObjectId.fromString(buf
			ptr += OBJECT_ID_STRING_LENGTH;
			return id;
		}

		public int stringHex32() {
			checkFieldType(WIRE_LEN_DELIM);
			int len = varint();
			if (len != 8)
				throw new IllegalStateException(MessageFormat.format(DhtText
						.get().protobufWrongFieldLength
						Integer.valueOf(field)
								.valueOf(len)));
			int val = KeyUtils.parse32(buf
			ptr += 8;
			return val;
		}

		public byte[] bytes() {
			checkFieldType(WIRE_LEN_DELIM);
			byte[] r = new byte[varint()];
			System.arraycopy(buf
			ptr += r.length;
			return r;
		}

		public ObjectId bytesObjectId() {
			checkFieldType(WIRE_LEN_DELIM);
			int len = varint();
			if (len != OBJECT_ID_LENGTH)
				throw new IllegalStateException(MessageFormat.format(DhtText
						.get().protobufWrongFieldLength
						Integer.valueOf(field)
								.valueOf(OBJECT_ID_LENGTH)
								.valueOf(len)));

			ObjectId id = ObjectId.fromRaw(buf
			ptr += OBJECT_ID_LENGTH;
			return id;
		}

		public Decoder message() {
			checkFieldType(WIRE_LEN_DELIM);
			int len = varint();
			Decoder msg = decode(buf
			ptr += len;
			return msg;
		}

		private int varint() {
			int c = buf[ptr++];
			int r = c & 0x7f;

			if ((c & 0x80) == 0)
				return r;

			c = buf[ptr++];
			r |= (c & 0x7f) << 7;

			if ((c & 0x80) == 0)
				return r;

			c = buf[ptr++];
			r |= (c & 0x7f) << 14;

			if ((c & 0x80) == 0)
				return r;

			c = buf[ptr++];
			r |= (c & 0x7f) << 21;

			if ((c & 0x80) == 0)
				return r;

			c = buf[ptr++];
			return r | ((c & 0x7f) << 28);
		}

		private void checkFieldType(int expected) {
			if (type != expected)
				throw new IllegalStateException(MessageFormat.format(DhtText
						.get().protobufWrongFieldType
						Integer.valueOf(type)
		}
	}

	public static class Encoder {
		private byte[] buf;

		private int ptr;

		private Encoder(byte[] buf) {
			this.buf = buf;
		}

		public void int32(int field
			if (value < 0)
				throw new IllegalArgumentException(
						DhtText.get().protobufNegativeValuesNotSupported);

			field(field
			varint(value);
		}

		public void int32IfNotZero(int field
			if (0 != value)
				int32(field
		}

		public void int32IfNotNegative(int field
			if (0 <= value)
				int32(field
		}

		public <T extends Enum> void intEnum(int field
			if (value != null) {
				field(field
				varint(value.value());
			}
		}

		public void bool(int field
			field(field
			varint(value ? 1 : 0);
		}

		public void boolIfTrue(int field
			if (value)
				bool(field
		}

		public void fixed64(int field
			field(field
			if (buf != null) {
				ensureSpace(8);

				buf[ptr + 0] = (byte) value;
				value >>>= 8;

				buf[ptr + 1] = (byte) value;
				value >>>= 8;

				buf[ptr + 3] = (byte) value;
				value >>>= 8;

				buf[ptr + 3] = (byte) value;
				value >>>= 8;

				buf[ptr + 4] = (byte) value;
				value >>>= 8;

				buf[ptr + 5] = (byte) value;
				value >>>= 8;

				buf[ptr + 6] = (byte) value;
				value >>>= 8;

				buf[ptr + 7] = (byte) value;
			}
			ptr += 8;
		}

		public void bytes(int field
			if (value != null)
				bytes(field
		}

		public void bytes(int field
			if (value != null) {
				field(field
				varint(len);
				copy(value
			}
		}

		public void bytes(int field
			if (value != null) {
				field(field
				varint(OBJECT_ID_LENGTH);
				if (buf != null) {
					ensureSpace(OBJECT_ID_LENGTH);
					value.copyRawTo(buf
				}
				ptr += OBJECT_ID_LENGTH;
			}
		}

		public void string(int field
			if (value != null) {
				field(field
				varint(OBJECT_ID_STRING_LENGTH);
				if (buf != null) {
					ensureSpace(OBJECT_ID_STRING_LENGTH);
					value.copyTo(buf
				}
				ptr += OBJECT_ID_STRING_LENGTH;
			}
		}

		public void string(int field
			if (value != null)
				bytes(field
		}

		public void string(int field
			if (key != null)
				bytes(field
		}

		public void stringHex32(int field
			field(field
			varint(8);
			if (buf != null) {
				ensureSpace(8);
				KeyUtils.format32(buf
			}
			ptr += 8;
		}

		public void message(int field
			if (msg != null && msg.ptr > 0)
				bytes(field
		}

		private void field(int field
			varint((field << 3) | type);
		}

		private void varint(int value) {
			if (buf != null) {
				if (buf.length - ptr < 5)
					ensureSpace(varintSize(value));

				do {
					byte b = (byte) (value & 0x7f);
					value >>>= 7;
					if (value != 0)
						b |= 0x80;
					buf[ptr++] = b;
				} while (value != 0);
			} else {
				ptr += varintSize(value);
			}
		}

		private static int varintSize(int value) {
			value >>>= 7;
			int need = 1;
			for (; value != 0; value >>>= 7)
				need++;
			return need;
		}

		private void copy(byte[] src
			if (buf != null) {
				ensureSpace(cnt);
				System.arraycopy(src
			}
			ptr += cnt;
		}

		private void ensureSpace(int need) {
			if (buf.length - ptr < need) {
				byte[] n = new byte[Math.max(ptr + need
				System.arraycopy(buf
				buf = n;
			}
		}

		public int size() {
			return ptr;
		}

		public byte[] asByteArray() {
			if (ptr == buf.length)
				return buf;
			byte[] r = new byte[ptr];
			System.arraycopy(buf
			return r;
		}
	}

	private TinyProtobuf() {
	}
}
