package org.eclipse.ui.actions;

public final class SimpleWildcardTester {
    public static boolean testWildcard(String pattern, String str) {
        if (pattern.equals("*")) {//$NON-NLS-1$
            return true;
        } else if (pattern.startsWith("*")) {//$NON-NLS-1$
            if (pattern.endsWith("*")) {//$NON-NLS-1$
                if (pattern.length() == 2) {
					return true;
				}
                return str.indexOf(pattern.substring(1, pattern.length() - 1)) >= 0;
            }
            return str.endsWith(pattern.substring(1));
        } else if (pattern.endsWith("*")) {//$NON-NLS-1$
            return str.startsWith(pattern.substring(0, pattern.length() - 1));
        } else {
            return str.equals(pattern);
        }
    }

    public static boolean testWildcardIgnoreCase(String pattern, String str) {

        if (str == null) {
			return false;
		}
        pattern = pattern.toLowerCase();
        str = str.toLowerCase();
        return testWildcard(pattern, str);
    }
}
