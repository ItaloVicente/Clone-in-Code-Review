
package org.eclipse.ui.activities;

import java.util.regex.Pattern;

public interface IActivityPatternBinding extends Comparable {

    String getActivityId();

	Pattern getPattern();
    
	String getString();

	boolean isEqualityPattern();
}
