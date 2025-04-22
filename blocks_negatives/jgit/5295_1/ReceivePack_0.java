				final int nul = line.indexOf('\0');
				if (nul >= 0) {
					for (String c : line.substring(nul + 1).split(" "))
						enabledCapabilities.add(c);
					line = line.substring(0, nul);
				}
