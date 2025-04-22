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
