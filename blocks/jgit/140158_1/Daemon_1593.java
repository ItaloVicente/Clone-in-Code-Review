
package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class CredentialsProviderUserInfo implements UserInfo
		UIKeyboardInteractive {
	private final URIish uri;

	private final CredentialsProvider provider;

	private String password;

	private String passphrase;

	public CredentialsProviderUserInfo(Session session
			CredentialsProvider credentialsProvider) {
		this.uri = createURI(session);
		this.provider = credentialsProvider;
	}

	private static URIish createURI(Session session) {
		URIish uri = new URIish();
		uri = uri.setUser(session.getUserName());
		uri = uri.setHost(session.getHost());
		uri = uri.setPort(session.getPort());
		return uri;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getPassphrase() {
		return passphrase;
	}

	@Override
	public boolean promptPassphrase(String msg) {
		CredentialItem.StringType v = newPrompt(msg);
		if (provider.get(uri
			passphrase = v.getValue();
			return true;
		} else {
			passphrase = null;
			return false;
		}
	}

	@Override
	public boolean promptPassword(String msg) {
		CredentialItem.Password p = new CredentialItem.Password(msg);
		if (provider.get(uri
			password = new String(p.getValue());
			return true;
		} else {
			password = null;
			return false;
		}
	}

	private CredentialItem.StringType newPrompt(String msg) {
		return new CredentialItem.StringType(msg
	}

	@Override
	public boolean promptYesNo(String msg) {
		CredentialItem.YesNoType v = new CredentialItem.YesNoType(msg);
		return provider.get(uri
	}

	@Override
	public void showMessage(String msg) {
		provider.get(uri
	}

	@Override
	public String[] promptKeyboardInteractive(String destination
			String instruction
		CredentialItem.StringType[] v = new CredentialItem.StringType[prompt.length];
		for (int i = 0; i < prompt.length; i++)
			v[i] = new CredentialItem.StringType(prompt[i]

		List<CredentialItem> items = new ArrayList<>();
		if (instruction != null && instruction.length() > 0)
			items.add(new CredentialItem.InformationalMessage(instruction));
		items.addAll(Arrays.asList(v));

		if (!provider.get(uri

		String[] result = new String[v.length];
		for (int i = 0; i < v.length; i++)
			result[i] = v[i].getValue();
		return result;
	}
}
