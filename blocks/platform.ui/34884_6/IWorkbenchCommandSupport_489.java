
package org.eclipse.ui.commands;

import org.eclipse.ui.keys.KeySequence;

@Deprecated
@SuppressWarnings("all")
public interface IKeySequenceBinding extends Comparable {

	@Deprecated
    KeySequence getKeySequence();
}
