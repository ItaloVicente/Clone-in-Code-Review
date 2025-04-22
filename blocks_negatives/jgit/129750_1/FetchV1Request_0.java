final class FetchV1Request {

	final Set<ObjectId> wantsIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> options;
