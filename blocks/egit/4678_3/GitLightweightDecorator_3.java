						} else {
							if (key.charAt(0) == ' ') {
								spaceBefore = true;
								key = key.substring(1);
							}
							if (key.charAt(key.length() - 1) == ' ') {
								spaceAfter = true;
								key = key.substring(0, key.length() - 1);
							}
