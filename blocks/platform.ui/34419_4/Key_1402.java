
package org.eclipse.ui.keys;

@Deprecated
public interface IKeyFormatter {

    String format(Key key);

    String format(KeySequence keySequence);

    String format(KeyStroke keyStroke);
}
