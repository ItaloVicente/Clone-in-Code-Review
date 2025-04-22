public class WhalinTranscoder extends BaseSerializingTranscoder
	implements Transcoder<Object> {

	static final int SPECIAL_BYTE = 1;
	static final int SPECIAL_BOOLEAN = 8192;
	static final int SPECIAL_INT = 4;
	static final int SPECIAL_LONG = 16384;
	static final int SPECIAL_CHARACTER = 16;
	static final int SPECIAL_STRING = 32;
	static final int SPECIAL_STRINGBUFFER = 64;
	static final int SPECIAL_FLOAT = 128;
	static final int SPECIAL_SHORT = 256;
	static final int SPECIAL_DOUBLE = 512;
	static final int SPECIAL_DATE = 1024;
	static final int SPECIAL_STRINGBUILDER = 2048;
	static final int SPECIAL_BYTEARRAY = 4096;
	static final int COMPRESSED = 2;
	static final int SERIALIZED = 8;
