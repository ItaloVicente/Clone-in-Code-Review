
package org.eclipse.jgit.pgm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.jgit.pgm.internal.CLIText;

public class CommandRef {
	private final Class<? extends TextBuiltin> impl;

	private final String name;

	private String usage;

	boolean common;

	CommandRef(Class<? extends TextBuiltin> clazz) {
		this(clazz
	}

	CommandRef(Class<? extends TextBuiltin> clazz
		this(clazz
		usage = cmd.usage();
		common = cmd.common();
	}

	private CommandRef(Class<? extends TextBuiltin> clazz
		impl = clazz;
		name = cn;
	}

	private static String guessName(Class<? extends TextBuiltin> clazz) {
		final StringBuilder s = new StringBuilder();

		boolean lastWasDash = true;
		for (char c : clazz.getSimpleName().toCharArray()) {
			if (Character.isUpperCase(c)) {
				if (!lastWasDash)
					s.append('-');
				lastWasDash = !lastWasDash;
				s.append(Character.toLowerCase(c));
			} else {
				s.append(c);
				lastWasDash = false;
			}
		}
		return s.toString();
	}

	public String getName() {
		return name;
	}

	public String getUsage() {
		return usage;
	}

	public boolean isCommon() {
		return common;
	}

	public String getImplementationClassName() {
		return impl.getName();
	}

	public ClassLoader getImplementationClassLoader() {
		return impl.getClassLoader();
	}

	public TextBuiltin create() {
		final Constructor<? extends TextBuiltin> c;
		try {
			c = impl.getDeclaredConstructor();
		} catch (SecurityException | NoSuchMethodException e) {
			throw new RuntimeException(MessageFormat.format(CLIText.get().cannotCreateCommand
		}
		c.setAccessible(true);

		final TextBuiltin r;
		try {
			r = c.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(MessageFormat.format(CLIText.get().cannotCreateCommand
		}
		r.setCommandName(getName());
		return r;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "CommandRef [impl=" + impl + "
				+ CLIText.get().resourceBundle().getString(usage) + "
				+ common + "]";
	}
}
