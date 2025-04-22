
package org.eclipse.jgit.http.server;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class HttpServerText extends TranslationBundle {

	public static HttpServerText get() {
		return NLS.getBundleFor(HttpServerText.class);
	}

}
