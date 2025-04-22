package org.eclipse.jgit.lib;

public interface ConfigEnum {
	String toConfigValue();

	boolean matchConfigValue(String in);
}
