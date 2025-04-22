
package org.eclipse.jgit.iplog;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCookieManager extends CookieHandler {
	private Map<String

	@Override
	public Map<String
			Map<String
		String host = hostOf(uri);

		Map<String
		if (map == null || map.isEmpty())
			return requestHeaders;

		Map<String
		r.putAll(requestHeaders);
		StringBuilder buf = new StringBuilder();
		for (Map.Entry<String
			if (buf.length() > 0)
				buf.append("; ");
			buf.append(e.getKey());
			buf.append('=');
			buf.append(e.getValue());
		}
		r.put("Cookie"
		return Collections.unmodifiableMap(r);
	}

	@Override
	public void put(URI uri
			throws IOException {
		List<String> list = responseHeaders.get("Set-Cookie");
		if (list == null || list.isEmpty()) {
			return;
		}

		String host = hostOf(uri);
		Map<String
		if (map == null) {
			map = new HashMap<String
			byHost.put(host
		}

		for (String hdr : list) {
			String attributes[] = hdr.split(";");
			String nameValue = attributes[0].trim();
			int eq = nameValue.indexOf('=');
			String name = nameValue.substring(0
			String value = nameValue.substring(eq + 1);

			map.put(name
		}
	}

	private String hostOf(URI uri) {
		StringBuilder key = new StringBuilder();
		key.append(uri.getScheme());
		key.append(':');
		key.append(uri.getHost());
		if (0 < uri.getPort())
			key.append(':' + uri.getPort());
		return key.toString();
	}
}
