
package org.eclipse.jgit.transport;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand.Result;

public interface ReceiveSession {
	public Repository getRepository();

	public RevWalk getRevWalk();

	public Map<String

	public Set<ObjectId> getAdvertisedObjects();

	public boolean isCheckReferencedObjectsAreReachable();

	public boolean isBiDirectionalPipe();

	public boolean isCheckReceivedObjects();

	public boolean isAllowCreates();

	public boolean isAllowDeletes();

	public boolean isAllowNonFastForwards();

	public PersonIdent getRefLogIdent();

	public RefFilter getRefFilter();

	public PreReceiveHook getPreReceiveHook();

	public PostReceiveHook getPostReceiveHook();

	public int getTimeout();

	public List<ReceiveCommand> getAllCommands();

	public void sendError(final String what);

	public void sendMessage(final String what);

	public void onPreReceive();

	public void onPostReceive();
}
