package org.eclipse.urischeme;

import java.util.Collection;

import org.eclipse.urischeme.internal.UriSchemeExtensionReader;

public interface IUriSchemeExtensionReader {

	public static class Scheme {

		private String uriScheme;
		private String uriSchemeDescription;

		public Scheme(String uriScheme, String uriSchemeDescription) {
			super();
			this.uriScheme = uriScheme;
			this.uriSchemeDescription = uriSchemeDescription;
		}

		public String getUriScheme() {
			return uriScheme;
		}

		public String getUriSchemeDescription() {
			return uriSchemeDescription;
		}

	}

	IUriSchemeExtensionReader INSTANCE = UriSchemeExtensionReader.getInstance();

	Collection<Scheme> getSchemes();

}
