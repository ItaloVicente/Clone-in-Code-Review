package org.eclipse.jgit.lib;

import java.util.List;

import org.eclipse.jgit.transport.ReceiveCommand;

public interface SingleRepoSubmissionFactory {

	OpenSubmission createSubmission(List<ReceiveCommand> refUpdates);
}
