package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.egit.ui.internal.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.ui.internal.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.synchronize.ISynchronizeManager;
import org.eclipse.team.ui.synchronize.ISynchronizeParticipant;
import org.eclipse.team.ui.synchronize.ISynchronizeParticipantReference;

public class GitSynchronize {

	public GitSynchronize(GitSynchronizeData data) {
		this(new GitSynchronizeDataSet(data));
	}

	public GitSynchronize(GitSynchronizeDataSet data) {
		GitSubscriberParticipant participant = getParticipant(data);
		participant.refresh(data);
	}

	private GitSubscriberParticipant getParticipant(GitSynchronizeDataSet data) {

		ISynchronizeManager synchronizeManager = TeamUI.getSynchronizeManager();
		ISynchronizeParticipantReference[] participants = synchronizeManager
				.get(GitSubscriberParticipant.PARTICIPANT_NAME);

		GitSubscriberParticipant participant;

		if (participants.length == 0) {
			participant = createDefaultParticipant(data);
		} else {
			try {
				participant = (GitSubscriberParticipant) participants[0].getParticipant();
				participant.reset(data);
			} catch (TeamException e1) {
				participant = createDefaultParticipant(data);
			}
		}
		return participant;
	}

	private GitSubscriberParticipant createDefaultParticipant(GitSynchronizeDataSet data) {
		GitSubscriberParticipant participant = new GitSubscriberParticipant(data);
		TeamUI.getSynchronizeManager().addSynchronizeParticipants(new ISynchronizeParticipant[] { participant });

		return participant;
	}

}
