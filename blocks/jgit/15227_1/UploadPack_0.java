			if (isFirst) {
				if (line.length() > 45) {
					FirstLine firstLine = new FirstLine(line);
					options = firstLine.getOptions();
					line = firstLine.getLine();
				} else
					options = Collections.emptySet();
