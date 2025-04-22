
package org.eclipse.ui.internal.menus;

import org.eclipse.ui.internal.util.Util;

public class MenuLocationURI {

	private String rawString;
	
	public MenuLocationURI(String uriDef) {
		rawString = uriDef;
	}

	public String getQuery() {
		String[] vals = Util.split(rawString, '?');
		return vals.length>1?vals[1]:Util.ZERO_LENGTH_STRING;
	}

	public String getScheme() {
		String[] vals = Util.split(rawString, ':');
		return vals[0];
	}

	public String getPath() {
		String[] vals = Util.split(rawString, ':');
		if (vals.length<2)
			return null;
		
		vals = Util.split(vals[1], '?');
		return vals.length==0?Util.ZERO_LENGTH_STRING:vals[0];
	}

	@Override
	public String toString() {
		return rawString;
	}

	public String getRawString() {
		return rawString;
	}
}
