package org.eclipse.egit.ui.internal.dialogs;

import java.text.MessageFormat;
import java.util.Collections;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.branch.LaunchFinder;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class CommandConfirmation {
	public static boolean confirmHardReset(Shell shell, final Repository repo) {
		String question = UIText.ResetTargetSelectionDialog_ResetConfirmQuestion;
		ILaunchConfiguration launch = LaunchFinder
				.getRunningLaunchConfiguration(Collections.singleton(repo),
						null);
		if (launch != null) {
			question = MessageFormat.format(question,
					"\n\n" + MessageFormat.format( //$NON-NLS-1$
							UIText.LaunchFinder_RunningLaunchMessage,
							launch.getName()));
		} else {
			question = MessageFormat.format(question, ""); //$NON-NLS-1$
		}
		return MessageDialog.openQuestion(shell,
				UIText.ResetTargetSelectionDialog_ResetQuestion, question);
	}
}
