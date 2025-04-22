					d.append(LF);
					int start = d.length();
					String pathLine = formatPathLine(path);
					int len = pathLine.length();
					d.append(pathLine).append(LF);
					styles.add(new StyleRange(start, len, darkGrey, null));
