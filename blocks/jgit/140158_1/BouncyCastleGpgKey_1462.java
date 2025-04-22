
package org.eclipse.jgit.lib;

import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.util.SystemReader;

public class UserConfig {
	public static final Config.SectionParser<UserConfig> KEY = UserConfig::new;

	private String authorName;

	private String authorEmail;

	private String committerName;

	private String committerEmail;

	private boolean isAuthorNameImplicit;

	private boolean isAuthorEmailImplicit;

	private boolean isCommitterNameImplicit;

	private boolean isCommitterEmailImplicit;

	private UserConfig(Config rc) {
		authorName = getNameInternal(rc
		if (authorName == null) {
			authorName = getDefaultUserName();
			isAuthorNameImplicit = true;
		}
		authorEmail = getEmailInternal(rc
		if (authorEmail == null) {
			authorEmail = getDefaultEmail();
			isAuthorEmailImplicit = true;
		}

		committerName = getNameInternal(rc
		if (committerName == null) {
			committerName = getDefaultUserName();
			isCommitterNameImplicit = true;
		}
		committerEmail = getEmailInternal(rc
		if (committerEmail == null) {
			committerEmail = getDefaultEmail();
			isCommitterEmailImplicit = true;
		}
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getCommitterName() {
		return committerName;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public String getCommitterEmail() {
		return committerEmail;
	}

	public boolean isAuthorNameImplicit() {
		return isAuthorNameImplicit;
	}

	public boolean isAuthorEmailImplicit() {
		return isAuthorEmailImplicit;
	}

	public boolean isCommitterNameImplicit() {
		return isCommitterNameImplicit;
	}

	public boolean isCommitterEmailImplicit() {
		return isCommitterEmailImplicit;
	}

	private static String getNameInternal(Config rc
		String username = system().getenv(envKey);

		if (username == null) {
			username = rc.getString("user"
		}

		return stripInvalidCharacters(username);
	}

	private static String getDefaultUserName() {
		String username = system().getProperty(Constants.OS_USER_NAME_KEY);
		if (username == null)
			username = Constants.UNKNOWN_USER_DEFAULT;
		return username;
	}

	private static String getEmailInternal(Config rc
		String email = system().getenv(envKey);

		if (email == null) {
			email = rc.getString("user"
		}

		return stripInvalidCharacters(email);
	}

	private static String stripInvalidCharacters(String s) {
		return s == null ? null : s.replaceAll("<|>|\n"
	}

	private static String getDefaultEmail() {
		String username = getDefaultUserName();
	}

	private static SystemReader system() {
		return SystemReader.getInstance();
	}
}
