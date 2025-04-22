
package org.eclipse.ui.internal.activities;

import java.util.regex.Pattern;

import org.eclipse.ui.activities.IActivityPatternBinding;
import org.eclipse.ui.internal.util.Util;

public final class ActivityPatternBinding implements IActivityPatternBinding {
    private final static int HASH_FACTOR = 89;

    private final static int HASH_INITIAL = ActivityPatternBinding.class
            .getName().hashCode();

    private String activityId;

    private transient int hashCode = HASH_INITIAL;

    private Pattern pattern;
    
    private String patternString;
    
    private boolean isEqualityPattern;

    private transient String string;
    
    public ActivityPatternBinding(String activityId, String pattern) {
    	this(activityId, Pattern.compile(pattern));
    }
    
    public ActivityPatternBinding(String activityId, String pattern, boolean
    		isEqualityPattern) {
    	if (pattern == null) {
			throw new NullPointerException();
		}
    	
    	this.activityId = activityId;
    	this.isEqualityPattern = isEqualityPattern;
    	if (isEqualityPattern) {
    		this.patternString = pattern;    		
    		this.pattern = null;
    	} else {    		
    		this.patternString = null;
    		this.pattern = Pattern.compile(pattern);
    	}    
    }

    public ActivityPatternBinding(String activityId, Pattern pattern) {
        if (pattern == null) {
			throw new NullPointerException();
		}

        this.activityId = activityId;
        this.pattern = pattern;
        this.isEqualityPattern = false;
        this.patternString = null;
    }

    @Override
	public int compareTo(Object object) {
        ActivityPatternBinding castedObject = (ActivityPatternBinding) object;
        int compareTo = Util.compare(activityId, castedObject.activityId);

        if (compareTo == 0) {        	
        	compareTo = Util.compare(isEqualityPattern,
					castedObject.isEqualityPattern);
        	
        	if (compareTo == 0)
				compareTo = Util.compare(getPattern().pattern(), castedObject
						.getPattern().pattern());
		}

        return compareTo;
    }

    @Override
	public boolean equals(Object object) {
        if (!(object instanceof ActivityPatternBinding)) {
			return false;
		}

        final ActivityPatternBinding castedObject = (ActivityPatternBinding) object;
        if (!Util.equals(activityId, castedObject.activityId)) {
            return false;
        }
        
        if (!Util.equals(isEqualityPattern, castedObject.isEqualityPattern)) {
            return false;
        }

        return Util.equals(getPattern(), castedObject.getPattern());
    }

    @Override
	public String getActivityId() {
        return activityId;
    }
    
    @Override
	public Pattern getPattern() {
    	if (pattern == null) {
    		pattern = Pattern.compile(PatternUtil.quotePattern(patternString));    		
    	}
    	return pattern;
    }
    
    @Override
	public String getString() {
    	if (isEqualityPattern) {
    		return patternString;
    	}
    	return getPattern().pattern();
    }
    
	@Override
	public boolean isEqualityPattern() {
		return isEqualityPattern;
	}
    

    @Override
	public int hashCode() {
        if (hashCode == HASH_INITIAL) {
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(activityId);
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(pattern);
            if (hashCode == HASH_INITIAL) {
				hashCode++;
			}
        }

        return hashCode;
    }

    @Override
	public String toString() {
        if (string == null) {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('[');
            stringBuffer.append(activityId);
            stringBuffer.append(',');
            stringBuffer.append(isEqualityPattern());
            stringBuffer.append(',');
            stringBuffer.append(getString());
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }

	public boolean isMatch(String toMatch) {
		if (isEqualityPattern) {
			return patternString.equals(toMatch);
		}
		return pattern.matcher(toMatch).matches();		
	}	
}
