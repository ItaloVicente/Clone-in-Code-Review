
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;

public class RemoteRefUpdate {
	public static enum Status {
		NOT_ATTEMPTED

		UP_TO_DATE

		REJECTED_NONFASTFORWARD

		REJECTED_NODELETE

		REJECTED_REMOTE_CHANGED

		REJECTED_OTHER_REASON

		NON_EXISTING

		AWAITING_REPORT

		OK;
	}

	private ObjectId expectedOldObjectId;

	private final ObjectId newObjectId;

	private final String remoteName;

	private final TrackingRefUpdate trackingRefUpdate;

	private final String srcRef;

	private final boolean forceUpdate;

	private Status status;

	private boolean fastForward;

	private String message;

	private final Repository localDb;

	private RefUpdate localUpdate;

	public RemoteRefUpdate(final Repository localDb
			final String remoteName
			final String localName
			throws IOException {
		this(localDb
				: ObjectId.zeroId()
				expectedOldObjectId);
	}

	public RemoteRefUpdate(final Repository localDb
			final String remoteName
			final String localName
			throws IOException {
		this(localDb
				srcRef != null ? srcRef.getObjectId() : null
				forceUpdate
	}

	public RemoteRefUpdate(final Repository localDb
			final ObjectId srcId
			final boolean forceUpdate
			final ObjectId expectedOldObjectId) throws IOException {
		if (remoteName == null)
			throw new IllegalArgumentException(JGitText.get().remoteNameCannotBeNull);
		if (srcId == null && srcRef != null)
			throw new IOException(MessageFormat.format(
					JGitText.get().sourceRefDoesntResolveToAnyObject

		if (srcRef != null)
			this.srcRef = srcRef;
		else if (srcId != null && !srcId.equals(ObjectId.zeroId()))
			this.srcRef = srcId.name();
		else
			this.srcRef = null;

		if (srcId != null)
			this.newObjectId = srcId;
		else
			this.newObjectId = ObjectId.zeroId();

		this.remoteName = remoteName;
		this.forceUpdate = forceUpdate;
		if (localName != null && localDb != null) {
			localUpdate = localDb.updateRef(localName);
			localUpdate.setForceUpdate(true);
			localUpdate.setRefLogMessage("push"
			localUpdate.setNewObjectId(newObjectId);
			trackingRefUpdate = new TrackingRefUpdate(
					true
					remoteName
					localName
					localUpdate.getOldObjectId() != null
						? localUpdate.getOldObjectId()
						: ObjectId.zeroId()
					newObjectId);
		} else
			trackingRefUpdate = null;
		this.localDb = localDb;
		this.expectedOldObjectId = expectedOldObjectId;
		this.status = Status.NOT_ATTEMPTED;
	}

	public RemoteRefUpdate(final RemoteRefUpdate base
			final ObjectId newExpectedOldObjectId) throws IOException {
		this(base.localDb
				(base.trackingRefUpdate == null ? null : base.trackingRefUpdate
						.getLocalName())
	}

	public ObjectId getExpectedOldObjectId() {
		return expectedOldObjectId;
	}

	public boolean isExpectingOldObjectId() {
		return expectedOldObjectId != null;
	}

	public ObjectId getNewObjectId() {
		return newObjectId;
	}

	public boolean isDelete() {
		return ObjectId.zeroId().equals(newObjectId);
	}

	public String getRemoteName() {
		return remoteName;
	}

	public TrackingRefUpdate getTrackingRefUpdate() {
		return trackingRefUpdate;
	}

	public String getSrcRef() {
		return srcRef;
	}

	public boolean hasTrackingRefUpdate() {
		return trackingRefUpdate != null;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isFastForward() {
		return fastForward;
	}

	public String getMessage() {
		return message;
	}

	void setExpectedOldObjectId(ObjectId id) {
		expectedOldObjectId = id;
	}

	void setStatus(Status status) {
		this.status = status;
	}

	void setFastForward(boolean fastForward) {
		this.fastForward = fastForward;
	}

	void setMessage(String message) {
		this.message = message;
	}

	protected void updateTrackingRef(RevWalk walk) throws IOException {
		if (isDelete())
			trackingRefUpdate.setResult(localUpdate.delete(walk));
		else
			trackingRefUpdate.setResult(localUpdate.update(walk));
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "RemoteRefUpdate[remoteName="
				+ remoteName
				+ "
				+ status
				+ "
				+ (expectedOldObjectId != null ? expectedOldObjectId.name()
						: "(null)") + "..."
				+ (newObjectId != null ? newObjectId.name() : "(null)")
				+ (fastForward ? "
				+ "
				+ (forceUpdate ? "
				+ (message != null ? "\"" + message + "\"" : "null") + "]";
	}
}
