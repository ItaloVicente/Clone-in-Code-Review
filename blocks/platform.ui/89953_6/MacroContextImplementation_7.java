package org.eclipse.e4.core.macros.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class JSONHelper {

	public static String quote(String string) {
		int len = string.length();
		if (len == 0) {
			return "\"\""; //$NON-NLS-1$
		}

		StringBuilder sb = new StringBuilder(len + 4);
		sb.append('"');

		for (int i = 0; i < len; i += 1) {
			char c = string.charAt(i);
			switch (c) {
			case '"':
			case '\\':
			case '/':
				sb.append('\\');
				sb.append(c);
				break;

			case '\b':
				sb.append("\\b"); //$NON-NLS-1$
				break;

			case '\f':
				sb.append("\\f"); //$NON-NLS-1$
				break;

			case '\n':
				sb.append("\\n"); //$NON-NLS-1$
				break;

			case '\r':
				sb.append("\\r"); //$NON-NLS-1$
				break;

			case '\t':
				sb.append("\\t"); //$NON-NLS-1$
				break;

			default:
				if (c < ' ') {
					String t = "000" + Integer.toHexString(c); //$NON-NLS-1$
					sb.append("\\u" + t.substring(t.length() - 4)); //$NON-NLS-1$
				} else {
					sb.append(c);
				}
			}
		}
		sb.append('"');
		return sb.toString();
	}

	public static String toJSon(Map<String, String> map) {
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		final StringBuilder buf = new StringBuilder("{"); //$NON-NLS-1$

		while (iterator.hasNext()) {
			if (buf.length() > 1) {
				buf.append(',');
			}
			Entry<String, String> entry = iterator.next();
			buf.append(quote(entry.getKey()));
			buf.append(':');
			buf.append(quote(entry.getValue()));
		}
		buf.append('}');
		return buf.toString();
	}
}
