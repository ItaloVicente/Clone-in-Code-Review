
package org.eclipse.jgit.pgm.internal;

import java.text.MessageFormat;
import java.util.Locale;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;
import org.kohsuke.args4j.Localizable;

public class CLIText extends TranslationBundle {
	public static class Format implements Localizable {
		final String text;

		Format(String text) {
			this.text = text;
		}

		@Override
		public String formatWithLocale(Locale locale
			return format(args);
		}

		@Override
		public String format(Object... args) {
			return MessageFormat.format(text
		}
	}

	public static Format format(String text) {
		return new Format(text);
	}

	public static CLIText get() {
		return NLS.getBundleFor(CLIText.class);
	}

	public static String formatLine(String line) {
		return MessageFormat.format(get().lineFormat
	}

	public static String fatalError(String message) {
		return MessageFormat.format(get().fatalError
	}

}
