public class BaseMessage extends SpyObject {
	/**
	 * The index of the magic field in a tap header.
	 */
	public static final int MAGIC_INDEX = 0;

	/**
	 * The length of the magic field in a tap header.
	 */
	public static final int MAGIC_FIELD_LENGTH = 1;

	/**
	 * The index of the opcode field in a tap header.
	 */
	public static final int OPCODE_INDEX = 1;

	/**
	 * The length of the opcode field in a tap header.
	 */
	public static final int OPCODE_FIELD_LENGTH = 1;

	/**
	 * The index of the key length field in a tap header.
	 */
	public static final int KEY_LENGTH_INDEX = 2;

	/**
	 * The length of the key length field in a tap header.
	 */
	public static final int KEY_LENGTH_FIELD_LENGTH = 2;

	/**
	 * The index of the extra length field in the tap header.
	 */
	public static final int EXTRA_LENGTH_INDEX = 4;

	/**
	 * The length of the extra length field in a tap header.
	 */
	public static final int EXTRA_LENGTH_FIELD_LENGTH = 1;

	/**
	 * The index of the data type field in the tap header.
	 */
	public static final int DATA_TYPE_INDEX = 5;

	/**
	 * The length of the data type field in a tap header.
	 */
	public static final int DATA_TYPE_FIELD_LENGTH = 1;

	/**
	 * The index of the vbucket field in the tap header.
	 */
	public static final int VBUCKET_INDEX = 6;

	/**
	 * The length of the vbucket field in a tap header.
	 */
	public static final int VBUCKET_FIELD_LENGTH = 2;

	/**
	 * The index of the total body field in the tap header.
	 */
	public static final int TOTAL_BODY_INDEX = 8;

	/**
	 * The length of the total body field in the tap header.
	 */
	public static final int TOTAL_BODY_FIELD_LENGTH = 4;

	/**
	 * The index of the opaque field in the tap header.
	 */
	public static final int OPAQUE_INDEX = 12;

	/**
	 * The length of the opaque field in the tap header.
	 */
	public static final int OPAQUE_FIELD_LENGTH = 4;

	/**
	 * The index of the cas field in the tap header.
	 */
	public static final int CAS_INDEX = 16;

	/**
	 * The length of the cas field in the tap header.
	 */
	public static final int CAS_FIELD_LENGTH = 8;

	/**
	 * The header length
	 */
