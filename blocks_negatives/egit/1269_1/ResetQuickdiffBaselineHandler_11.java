/*******************************************************************************
 * Copyright (C) 2010, Mathias Kinzler <mathias.kinzler@sap.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.ResetOperation;
import org.eclipse.egit.core.op.ResetOperation.ResetType;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * "Reset" with parameter (hard, mixed, soft).
 */
public class ResetHandler extends AbstractHistoryCommanndHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repo = getRepository(event);
		final RevCommit commit = (RevCommit) getSelection(event)
				.getFirstElement();

		String type = event.getParameter(HistoryViewCommands.RESET_MODE);
		final ResetType resetType;

			resetType = ResetType.HARD;
			resetType = ResetType.MIXED;
			resetType = ResetType.SOFT;
		} else {
		}

		switch (resetType) {
		case HARD:
			if (!MessageDialog.openQuestion(HandlerUtil
					.getActiveShellChecked(event),
					UIText.ResetTargetSelectionDialog_ResetQuestion,
					UIText.ResetTargetSelectionDialog_ResetConfirmQuestion))
				return null;

			jobName = UIText.HardResetToRevisionAction_hardReset;
			break;
		case SOFT:
			jobName = UIText.SoftResetToRevisionAction_softReset;
			break;
		case MIXED:
			jobName = UIText.MixedResetToRevisionAction_mixedReset;
			break;
		}

		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new ResetOperation(repo, commit.getName(), resetType)
							.execute(null);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return null;
	}
}
