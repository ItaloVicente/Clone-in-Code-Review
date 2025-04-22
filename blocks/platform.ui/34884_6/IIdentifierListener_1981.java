
package org.eclipse.ui.activities;

import java.util.Set;

public interface IIdentifier extends Comparable {

    void addIdentifierListener(IIdentifierListener identifierListener);

    Set getActivityIds();

    String getId();

    boolean isEnabled();

    void removeIdentifierListener(IIdentifierListener identifierListener);
}
