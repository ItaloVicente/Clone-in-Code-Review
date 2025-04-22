
package org.eclipse.jgit.pgm;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CommandCatalog {
	private static final CommandCatalog INSTANCE = new CommandCatalog();

	public static CommandRef get(String name) {
		return INSTANCE.commands.get(name);
	}

	public static CommandRef[] all() {
		return toSortedArray(INSTANCE.commands.values());
	}

	public static CommandRef[] common() {
		final ArrayList<CommandRef> common = new ArrayList<>();
		for (CommandRef c : INSTANCE.commands.values())
			if (c.isCommon())
				common.add(c);
		return toSortedArray(common);
	}

	private static CommandRef[] toSortedArray(Collection<CommandRef> c) {
		final CommandRef[] r = c.toArray(new CommandRef[0]);
		Arrays.sort(r
			@Override
			public int compare(CommandRef o1
				return o1.getName().compareTo(o2.getName());
			}
		});
		return r;
	}

	private final ClassLoader ldr;

	private final Map<String

	private CommandCatalog() {
		ldr = Thread.currentThread().getContextClassLoader();
		commands = new HashMap<>();

		final Enumeration<URL> catalogs = catalogs();
		while (catalogs.hasMoreElements())
			scan(catalogs.nextElement());
	}

	private Enumeration<URL> catalogs() {
		try {
			return ldr.getResources(pfx + TextBuiltin.class.getName());
		} catch (IOException err) {
			return new Vector<URL>().elements();
		}
	}

	private void scan(URL cUrl) {
		try (BufferedReader cIn = new BufferedReader(
				new InputStreamReader(cUrl.openStream()
			String line;
			while ((line = cIn.readLine()) != null) {
					load(line);
			}
		} catch (IOException e) {
		}
	}

	private void load(String cn) {
		final Class<? extends TextBuiltin> clazz;
		try {
			clazz = Class.forName(cn
		} catch (ClassNotFoundException | ClassCastException notBuiltin) {
			return;
		}

		final CommandRef cr;
		final Command a = clazz.getAnnotation(Command.class);
		if (a != null)
			cr = new CommandRef(clazz
		else
			cr = new CommandRef(clazz);

		commands.put(cr.getName()
	}
}
