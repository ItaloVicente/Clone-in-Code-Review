package org.eclipse.jgit.ignore.internal;

public interface IMatcher {

	public static final IMatcher NO_MATCH = new IMatcher() {
		@Override
		public boolean matches(String path
				boolean pathMatch) {
			return false;
		}

		@Override
		public boolean matches(String segment
			return false;
		}
	};

	boolean matches(String path

	boolean matches(String segment
}
