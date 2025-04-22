
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class CLIText extends TranslationBundle {

	public static CLIText get() {
		return NLS.getBundleFor(CLIText.class);
	}

	public String commit;

	public String authorInfo;

	public String dateInfo;

	public String fromURI;

	public String remoteMessage;

	public String conflictingUsageOf_git_dir_andArguments;

	public String cannotGuessLocalNameFrom;

	public String initializedEmptyGitRepository;

	public String cannotChetkoutNoHeadsAdvertisedByRemote;
}
