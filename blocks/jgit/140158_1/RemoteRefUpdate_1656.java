
package org.eclipse.jgit.transport;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.lib.Config;

public class RemoteConfig implements Serializable {
	private static final long serialVersionUID = 1L;













	private static final boolean DEFAULT_MIRROR = false;



	public static List<RemoteConfig> getAllRemoteConfigs(Config rc)
			throws URISyntaxException {
		final List<String> names = new ArrayList<>(rc
				.getSubsections(SECTION));
		Collections.sort(names);

		final List<RemoteConfig> result = new ArrayList<>(names
				.size());
		for (String name : names)
			result.add(new RemoteConfig(rc
		return result;
	}

	private String name;

	private List<URIish> uris;

	private List<URIish> pushURIs;

	private List<RefSpec> fetch;

	private List<RefSpec> push;

	private String uploadpack;

	private String receivepack;

	private TagOpt tagopt;

	private boolean mirror;

	private int timeout;

	public RemoteConfig(Config rc
			throws URISyntaxException {
		name = remoteName;

		String[] vlst;
		String val;

		vlst = rc.getStringList(SECTION
		Map<String
		uris = new ArrayList<>(vlst.length);
		for (String s : vlst) {
			uris.add(new URIish(replaceUri(s
		}
		String[] plst = rc.getStringList(SECTION
		pushURIs = new ArrayList<>(plst.length);
		for (String s : plst) {
			pushURIs.add(new URIish(s));
		}
		if (pushURIs.isEmpty()) {
			Map<String
					KEY_PUSHINSTEADOF);
			if (!pushInsteadOf.isEmpty()) {
				for (String s : vlst) {
					String replaced = replaceUri(s
					if (!s.equals(replaced)) {
						pushURIs.add(new URIish(replaced));
					}
				}
			}
		}
		fetch = rc.getRefSpecs(SECTION
		push = rc.getRefSpecs(SECTION
		val = rc.getString(SECTION
		if (val == null) {
			val = DEFAULT_UPLOAD_PACK;
		}
		uploadpack = val;

		val = rc.getString(SECTION
		if (val == null) {
			val = DEFAULT_RECEIVE_PACK;
		}
		receivepack = val;

		try {
			val = rc.getString(SECTION
			tagopt = TagOpt.fromOption(val);
		} catch (IllegalArgumentException e) {
			tagopt = TagOpt.AUTO_FOLLOW;
		}
		mirror = rc.getBoolean(SECTION
		timeout = rc.getInt(SECTION
	}

	public void update(Config rc) {
		final List<String> vlst = new ArrayList<>();

		vlst.clear();
		for (URIish u : getURIs())
			vlst.add(u.toPrivateString());
		rc.setStringList(SECTION

		vlst.clear();
		for (URIish u : getPushURIs())
			vlst.add(u.toPrivateString());
		rc.setStringList(SECTION

		vlst.clear();
		for (RefSpec u : getFetchRefSpecs())
			vlst.add(u.toString());
		rc.setStringList(SECTION

		vlst.clear();
		for (RefSpec u : getPushRefSpecs())
			vlst.add(u.toString());
		rc.setStringList(SECTION

		set(rc
		set(rc
		set(rc
		set(rc
		set(rc
	}

	private void set(final Config rc
			final String currentValue
		if (defaultValue.equals(currentValue))
			unset(rc
		else
			rc.setString(SECTION
	}

	private void set(final Config rc
			final boolean currentValue
		if (defaultValue == currentValue)
			unset(rc
		else
			rc.setBoolean(SECTION
	}

	private void set(final Config rc
			final int defaultValue) {
		if (defaultValue == currentValue)
			unset(rc
		else
			rc.setInt(SECTION
	}

	private void unset(Config rc
		rc.unset(SECTION
	}

	private Map<String
			final String keyName) {
		final Map<String
		for (String url : config.getSubsections(KEY_URL))
			for (String insteadOf : config.getStringList(KEY_URL
				replacements.put(insteadOf
		return replacements;
	}

	private String replaceUri(final String uri
			final Map<String
		if (replacements.isEmpty())
			return uri;
		Entry<String
		for (Entry<String
			if (match != null
					&& match.getKey().length() > replacement.getKey().length())
				continue;
			if (!uri.startsWith(replacement.getKey()))
				continue;
			match = replacement;
		}
		if (match != null)
			return match.getValue() + uri.substring(match.getKey().length());
		else
			return uri;
	}

	public String getName() {
		return name;
	}

	public List<URIish> getURIs() {
		return Collections.unmodifiableList(uris);
	}

	public boolean addURI(URIish toAdd) {
		if (uris.contains(toAdd))
			return false;
		return uris.add(toAdd);
	}

	public boolean removeURI(URIish toRemove) {
		return uris.remove(toRemove);
	}

	public List<URIish> getPushURIs() {
		return Collections.unmodifiableList(pushURIs);
	}

	public boolean addPushURI(URIish toAdd) {
		if (pushURIs.contains(toAdd))
			return false;
		return pushURIs.add(toAdd);
	}

	public boolean removePushURI(URIish toRemove) {
		return pushURIs.remove(toRemove);
	}

	public List<RefSpec> getFetchRefSpecs() {
		return Collections.unmodifiableList(fetch);
	}

	public boolean addFetchRefSpec(RefSpec s) {
		if (fetch.contains(s))
			return false;
		return fetch.add(s);
	}

	public void setFetchRefSpecs(List<RefSpec> specs) {
		fetch.clear();
		fetch.addAll(specs);
	}

	public void setPushRefSpecs(List<RefSpec> specs) {
		push.clear();
		push.addAll(specs);
	}

	public boolean removeFetchRefSpec(RefSpec s) {
		return fetch.remove(s);
	}

	public List<RefSpec> getPushRefSpecs() {
		return Collections.unmodifiableList(push);
	}

	public boolean addPushRefSpec(RefSpec s) {
		if (push.contains(s))
			return false;
		return push.add(s);
	}

	public boolean removePushRefSpec(RefSpec s) {
		return push.remove(s);
	}

	public String getUploadPack() {
		return uploadpack;
	}

	public String getReceivePack() {
		return receivepack;
	}

	public TagOpt getTagOpt() {
		return tagopt;
	}

	public void setTagOpt(TagOpt option) {
		tagopt = option != null ? option : TagOpt.AUTO_FOLLOW;
	}

	public boolean isMirror() {
		return mirror;
	}

	public void setMirror(boolean m) {
		mirror = m;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int seconds) {
		timeout = seconds;
	}
}
