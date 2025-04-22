			if (StringUtils.equalsIgnoreCase("HostName", keyword)) { //$NON-NLS-1$
				for (final Host c : current)
					if (c.hostName == null)
						c.hostName = dequote(argValue);
			} else if (StringUtils.equalsIgnoreCase("User", keyword)) { //$NON-NLS-1$
				for (final Host c : current)
					if (c.user == null)
						c.user = dequote(argValue);
			} else if (StringUtils.equalsIgnoreCase("Port", keyword)) { //$NON-NLS-1$
				try {
					final int port = Integer.parseInt(dequote(argValue));
					for (final Host c : current)
						if (c.port == 0)
							c.port = port;
				} catch (NumberFormatException nfe) {
