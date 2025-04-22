
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.util.FS;

public class NetRC {


	public static class NetRCEntry {
		public String login;

		public char[] password;

		public String machine;

		public String account;

		public String macdef;

		public String macbody;

		public NetRCEntry() {
		}

		boolean complete() {
			return login != null && password != null && machine != null;
		}
	}

	private File netrc;

	private long lastModified;

	private Map<String

	private static final TreeMap<String
		private static final long serialVersionUID = -4285910831814853334L;
		{
			put("machine"
			put("login"
			put("password"
			put(DEFAULT_ENTRY
			put("account"
			put("macdef"
		}
	};

	enum State {
		COMMAND
	}

	public NetRC() {
		netrc = getDefaultFile();
		if (netrc != null)
			parse();
	}

	public NetRC(File netrc) {
		this.netrc = netrc;
		parse();
	}

	private static File getDefaultFile() {
		File home = FS.DETECTED.userHome();
		File netrc = new File(home
		if (netrc.exists())
			return netrc;

		netrc = new File(home
		if (netrc.exists())
			return netrc;

		return null;
	}

	public NetRCEntry getEntry(String host) {
		if (netrc == null)
			return null;

		if (this.lastModified != this.netrc.lastModified())
			parse();

		NetRCEntry entry = this.hosts.get(host);

		if (entry == null)
			entry = this.hosts.get(DEFAULT_ENTRY);

		return entry;
	}

	public Collection<NetRCEntry> getEntries() {
		return hosts.values();
	}

	private void parse() {
		this.hosts.clear();
		this.lastModified = this.netrc.lastModified();

		try (BufferedReader r = new BufferedReader(
				new InputStreamReader(new FileInputStream(netrc)
			String line = null;

			NetRCEntry entry = new NetRCEntry();

			State state = State.COMMAND;


			while ((line = r.readLine()) != null) {

				if (entry.macdef != null && entry.macbody == null) {
					if (line.length() == 0) {
						entry.macbody = macbody;
						continue;
					}
					continue;
				}

				matcher.reset(line);
				while (matcher.find()) {
					String command = matcher.group().toLowerCase(Locale.ROOT);
						continue;
					}
					state = STATE.get(command);
					if (state == null)
						state = State.COMMAND;

					switch (state) {
					case COMMAND:
						break;
					case ACCOUNT:
						if (entry.account != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						if (matcher.find())
							entry.account = matcher.group();
						state = State.COMMAND;
						break;
					case LOGIN:
						if (entry.login != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						if (matcher.find())
							entry.login = matcher.group();
						state = State.COMMAND;
						break;
					case PASSWORD:
						if (entry.password != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						if (matcher.find())
							entry.password = matcher.group().toCharArray();
						state = State.COMMAND;
						break;
					case DEFAULT:
						if (entry.machine != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.machine = DEFAULT_ENTRY;
						state = State.COMMAND;
						break;
					case MACDEF:
						if (entry.macdef != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						if (matcher.find())
							entry.macdef = matcher.group();
						state = State.COMMAND;
						break;
					case MACHINE:
						if (entry.machine != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						if (matcher.find())
							entry.machine = matcher.group();
						state = State.COMMAND;
						break;
					}
				}
			}

			if (entry.macdef != null && entry.macbody == null)
				entry.macbody = macbody;

			if (entry.complete())
				hosts.put(entry.machine
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
