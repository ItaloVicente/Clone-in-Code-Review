package org.eclipse.jgit.internal.submodule;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

public class SubmoduleValidator {

	public static class SubmoduleValidationException extends Exception {

		public SubmoduleValidationException(String message) {
			super(message);
		}

		private static final long serialVersionUID = 1L;
	}

	public static void assertValidSubmoduleName(String name)
			throws SubmoduleValidationException {
			throw new SubmoduleValidationException(MessageFormat
					.format(JGitText.get().invalidNameContainsDotDot
		}

			throw new SubmoduleValidationException(
					MessageFormat.format(
							JGitText.get().submoduleNameInvalid
		}
	}

	@SuppressWarnings("unused")
	public static void assertValidSubmoduleUri(String uri)
			throws SubmoduleValidationException {
			throw new SubmoduleValidationException(
					MessageFormat.format(
							JGitText.get().submoduleUrlInvalid
		}
	}

	public static void assertValidSubmodulePath(String path)
			throws SubmoduleValidationException {

			throw new SubmoduleValidationException(
					MessageFormat.format(
							JGitText.get().submodulePathInvalid
		}
	}

	public static void assertValidGitModulesFile(String gitModulesContents)
			throws IOException {
		Config c = new Config();
		try {
			c.fromText(gitModulesContents);
			for (String subsection : c.getSubsections(
					ConfigConstants.CONFIG_SUBMODULE_SECTION)) {
				String url = c.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						subsection
				assertValidSubmoduleUri(url);

				assertValidSubmoduleName(subsection);

				String path = c.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						ConfigConstants.CONFIG_KEY_PATH);
				assertValidSubmodulePath(path);
			}
		} catch (ConfigInvalidException e) {
			throw new IOException(
					MessageFormat.format(
							JGitText.get().invalidGitModules
							e));
		} catch (SubmoduleValidationException e) {
			throw new IOException(e.getMessage()
		}
	}
}
