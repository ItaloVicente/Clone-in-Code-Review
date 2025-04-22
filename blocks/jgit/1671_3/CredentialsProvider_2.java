
package org.eclipse.jgit.transport;

import java.util.Arrays;

import org.eclipse.jgit.JGitText;

public abstract class CredentialItem {
	private final String promptText;

	private final boolean valueSecure;

	public CredentialItem(String promptText
		this.promptText = promptText;
		this.valueSecure = maskValue;
	}

	public String getPromptText() {
		return promptText;
	}

	public boolean isValueSecure() {
		return valueSecure;
	}

	public abstract void clear();

	public static class StringType extends CredentialItem {
		private String value;

		public StringType(String promptText
			super(promptText
		}

		@Override
		public void clear() {
			value = null;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String newValue) {
			value = newValue;
		}
	}

	public static class CharArrayType extends CredentialItem {
		private char[] value;

		public CharArrayType(String promptText
			super(promptText
		}

		@Override
		public void clear() {
			if (value != null) {
				Arrays.fill(value
				value = null;
			}
		}

		public char[] getValue() {
			return value;
		}

		public void setValue(char[] newValue) {
			clear();

			if (newValue != null) {
				value = new char[newValue.length];
				System.arraycopy(newValue
			}
		}

		public void setValueNoCopy(char[] newValue) {
			clear();
			value = newValue;
		}
	}

	public static class Username extends StringType {
		public Username() {
			super(JGitText.get().credentialUsername
		}
	}

	public static class Password extends CharArrayType {
		public Password() {
			super(JGitText.get().credentialPassword
		}
	}
}
