
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class DhtText extends TranslationBundle {
	public static DhtText get() {
		return NLS.getBundleFor(DhtText.class);
	}

}
