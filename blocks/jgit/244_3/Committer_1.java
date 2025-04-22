
package org.eclipse.jgit.iplog;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class CSV {
	private final BufferedReader in;

	private List<String> columns;

	CSV(BufferedReader br) throws IOException {
		in = br;
		columns = readLine();
	}

	Map<String
		List<String> row = readLine();
		if (columns == null || row == null)
			return null;

		Map<String
		for (int col = 0; col < columns.size(); col++)
			r.put(columns.get(col)
		return r;
	}

	private List<String> readLine() throws IOException {
		String line = in.readLine();
		if (line == null || line.length() == 0)
			return null;

		ArrayList<String> row;
		if (columns != null)
			row = new ArrayList<String>(columns.size());
		else
			row = new ArrayList<String>();

		int p = 0;
		while (p < line.length()) {
			if (line.charAt(p) == '"') {

				StringBuilder b = new StringBuilder();
				SCAN: while (p < line.length()) {
					char c = line.charAt(p);
					switch (c) {
					case '"':
						p++;
						break SCAN;

					case '\\':
						b.append(line.charAt(p + 1));
						p += 2;
						break;

					default:
						b.append(c);
						p++;
						break;
					}
				}
				if (p < line.length() && line.charAt(p) != '
					throw new IOException("CSV parsing error: " + line);
				row.add(b.toString());

			} else if (line.charAt(p) == '
				row.add("");
				p++;

			} else {
				int comma = line.indexOf('
				row.add(line.substring(p
				p = comma + 1;
			}
		}
		return row;
	}
}
