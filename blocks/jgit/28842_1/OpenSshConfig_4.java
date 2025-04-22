			} else if (StringUtils.equalsIgnoreCase(
					"ConnectionAttempts"
				try {
					final int connectionAttempts = Integer.parseInt(dequote(argValue));
					if (connectionAttempts > 0) {
						for (final Host c : current)
							if (c.connectionAttempts == 0)
								c.connectionAttempts = connectionAttempts;
					}
				} catch (NumberFormatException nfe) {
				}
