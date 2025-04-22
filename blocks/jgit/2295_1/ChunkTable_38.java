
package org.eclipse.jgit.storage.dht;

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
		return new Encoder(estimatedSize);
	}

	public static Decoder decode(byte[] buf) {
		return decode(buf
	}

	public static Decoder decode(byte[] buf
		return new Decoder(buf
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
				throw new IllegalStateException(MessageFormat.format(
						DhtText.get().protobufUnsupportedFieldType
			}
		}

		public int int32() {
			checkFieldType(WIRE_VARINT);
			return varint();
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
				throw new IllegalStateException(MessageFormat.format(
						DhtText.get().protobufNotBooleanValue
			}
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
				throw new IllegalStateException(MessageFormat.format(
						DhtText.get().protobufWrongFieldLength
						OBJECT_ID_STRING_LENGTH

			ObjectId id = ObjectId.fromString(buf
			ptr += OBJECT_ID_STRING_LENGTH;
			return id;
		}

		public byte[] bytes() {
			checkFieldType(WIRE_LEN_DELIM);
			byte[] r = new byte[varint()];
			System.arraycopy(buf
			ptr += r.length;
			return r;
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
				throw new IllegalStateException(MessageFormat.format(
						DhtText.get().protobufWrongFieldType
						expected));
		}
	}

	public static class Encoder {
		private byte[] buf;

		private int ptr;

		private Encoder(int estimatedSize) {
			buf = new byte[estimatedSize];
		}

		public void int32(int field
			if (value < 0)
				throw new IllegalArgumentException(
						DhtText.get().protobufNegativeValuesNotSupported);

			field(field
			varint(value);
		}

		public void bool(int field
			field(field
			varint(value ? 1 : 0);
		}

		public void bytes(int field
			bytes(field
		}

		public void bytes(int field
			field(field
			varint(len);
			copy(value
		}

		public void string(int field
			field(field
			varint(OBJECT_ID_STRING_LENGTH);
			ensureSpace(OBJECT_ID_STRING_LENGTH);
			value.copyTo(buf
			ptr += OBJECT_ID_STRING_LENGTH;
		}

		public void string(int field
			bytes(field
		}

		public void string(int field
			bytes(field
		}

		public void message(int field
			bytes(field
		}

		private void field(int field
			varint((field << 3) | type);
		}

		private void varint(int value) {
			if (buf.length - ptr < 5) {
				int v = value >>> 7;
				int need = 1;
				for (; v != 0; v >>>= 7)
					need++;
				ensureSpace(need);
			}

			do {
				byte b = (byte) (value & 0x7f);
				value >>>= 7;
				if (value != 0)
					b |= 0x80;
				buf[ptr++] = b;
			} while (value != 0);
		}

		private void copy(byte[] src
			ensureSpace(cnt);
			System.arraycopy(src
			ptr += cnt;
		}

		private void ensureSpace(int need) {
			if (buf.length - ptr < need) {
				byte[] n = new byte[Math.max(ptr + need
				System.arraycopy(buf
				buf = n;
			}
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
