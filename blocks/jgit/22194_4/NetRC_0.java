
package org.eclipse.jgit.transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetRC {

	public static class NetRCEntry {
		public String login;

		public char[] password;

		public Boolean def;

		public String machine;

		public String account;

		public String macdef;

		public NetRCEntry() {
		}

		boolean complete() {
			return login != null && password != null && machine != null;
		}
	}

	File netrc;

	long lastModified;

	Map<String

	enum State {
		COMMAND
	}

	public NetRC() {
		netrc = getDefaultFile();
		parse();
	}

	public NetRC(File netrc) {
		this.netrc = netrc;
		parse();
	}

	private static File getDefaultFile() {
		File netrc;


		netrc = new File(home
		if (netrc.exists())
			return netrc;

		netrc = new File(home
		if (netrc.exists())
			return netrc;

		return null;
	}

	public NetRCEntry entry(String host) {
		if (this.lastModified != this.netrc.lastModified())
			parse();
		return this.hosts.get(host);
	}

	public Collection<NetRCEntry> entries() {
		return hosts.values();
	}

	TreeMap<String
		private static final long serialVersionUID = -4285910831814853334L;
		{
			put("machine"
			put("login"
			put("password"
			put("default"
			put("account"
			put("macdef"
		}
	};

	void parse() {
		this.hosts.clear();
		this.lastModified = this.netrc.lastModified();

		BufferedReader r = null;
		try {
			r = new BufferedReader(new FileReader(netrc));
			String line = null;

			NetRCEntry entry = new NetRCEntry();

			State state = State.COMMAND;

			while ((line = r.readLine()) != null) {
				matcher.reset(line);
				while (matcher.find()) {
					String match = matcher.group();
					switch (state) {
					case COMMAND:
						String command = match.toLowerCase();
							continue;
						}
						state = STATE.get(command);
						if (state == null)
							state = State.COMMAND;
						break;
					case ACCOUNT:
						if (entry.account != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.account = match;
						state = State.COMMAND;
						break;
					case LOGIN:
						if (entry.login != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.login = match;
						state = State.COMMAND;
						break;
					case PASSWORD:
						if (entry.password != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.password = match.toCharArray();
						state = State.COMMAND;
						break;
					case DEFAULT:
						if (entry.def != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.def = new Boolean(true);
						state = State.COMMAND;
						break;
					case MACDEF:
						if (entry.macdef != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.macdef = match;
						state = State.COMMAND;
						break;
					case MACHINE:
						if (entry.machine != null && entry.complete()) {
							hosts.put(entry.machine
							entry = new NetRCEntry();
						}
						entry.machine = match;
						state = State.COMMAND;
						break;
					}
				}
			}

			if (entry.complete())
				hosts.put(entry.machine
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (r != null)
					r.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
