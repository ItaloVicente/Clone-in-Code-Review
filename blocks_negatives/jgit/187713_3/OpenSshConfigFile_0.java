				result.add(argument.substring(start, stop));
				start = stop + 1;
			} else {
				int stop = start + 1;
				while (stop < length
						&& !Character.isWhitespace(argument.charAt(stop))) {
					stop++;
