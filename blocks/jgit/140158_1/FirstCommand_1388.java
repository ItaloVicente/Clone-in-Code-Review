package org.eclipse.jgit.internal.submodule;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PATH;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_URL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_SUBMODULE_SECTION;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.GITMODULES_NAME;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.GITMODULES_PARSE;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.GITMODULES_PATH;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.GITMODULES_URL;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectChecker;

public class SubmoduleValidator {

	public static class SubmoduleValidationException extends Exception {

		private static final long serialVersionUID = 1L;

		private final ObjectChecker.ErrorType fsckMessageId;

		SubmoduleValidationException(String message
				ObjectChecker.ErrorType fsckMessageId) {
			super(message);
			this.fsckMessageId = fsckMessageId;
		}


		public ObjectChecker.ErrorType getFsckMessageId() {
			return fsckMessageId;
		}
	}

	public static void assertValidSubmoduleName(String name)
			throws SubmoduleValidationException {
			throw new SubmoduleValidationException(MessageFormat
					.format(JGitText.get().invalidNameContainsDotDot
					GITMODULES_NAME);
		}

			throw new SubmoduleValidationException(
					MessageFormat.format(
							JGitText.get().submoduleNameInvalid
					GITMODULES_NAME);
		}
	}

	public static void assertValidSubmoduleUri(String uri)
			throws SubmoduleValidationException {
			throw new SubmoduleValidationException(
					MessageFormat.format(
							JGitText.get().submoduleUrlInvalid
					GITMODULES_URL);
		}
	}

	public static void assertValidSubmodulePath(String path)
			throws SubmoduleValidationException {
			throw new SubmoduleValidationException(
					MessageFormat.format(
							JGitText.get().submodulePathInvalid
					GITMODULES_PATH);
		}
	}

	public static void assertValidGitModulesFile(String gitModulesContents)
			throws SubmoduleValidationException {
		Config c = new Config();
		try {
			c.fromText(gitModulesContents);
			for (String subsection :
					c.getSubsections(CONFIG_SUBMODULE_SECTION)) {
				assertValidSubmoduleName(subsection);

				String url = c.getString(
						CONFIG_SUBMODULE_SECTION
				if (url != null) {
					assertValidSubmoduleUri(url);
				}

				String path = c.getString(
						CONFIG_SUBMODULE_SECTION
				if (path != null) {
					assertValidSubmodulePath(path);
				}
			}
		} catch (ConfigInvalidException e) {
			throw new SubmoduleValidationException(
					JGitText.get().invalidGitModules
					GITMODULES_PARSE);
		}
	}
}
