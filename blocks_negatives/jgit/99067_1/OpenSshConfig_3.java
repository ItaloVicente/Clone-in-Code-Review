			} else if (StringUtils.equalsIgnoreCase("IdentityFile", keyword)) { //$NON-NLS-1$
				for (final Host c : current)
					if (c.identityFile == null)
						c.identityFile = toFile(dequote(argValue));
			} else if (StringUtils.equalsIgnoreCase(
					"PreferredAuthentications", keyword)) { //$NON-NLS-1$
				for (final Host c : current)
					if (c.preferredAuthentications == null)
						c.preferredAuthentications = nows(dequote(argValue));
			} else if (StringUtils.equalsIgnoreCase("BatchMode", keyword)) { //$NON-NLS-1$
				for (final Host c : current)
					if (c.batchMode == null)
						c.batchMode = yesno(dequote(argValue));
			} else if (StringUtils.equalsIgnoreCase(
					"StrictHostKeyChecking", keyword)) { //$NON-NLS-1$
				String value = dequote(argValue);
				for (final Host c : current)
					if (c.strictHostKeyChecking == null)
						c.strictHostKeyChecking = value;
			} else if (StringUtils.equalsIgnoreCase(
					"ConnectionAttempts", keyword)) { //$NON-NLS-1$
				try {
					final int connectionAttempts = Integer.parseInt(dequote(argValue));
					if (connectionAttempts > 0) {
						for (final Host c : current)
							if (c.connectionAttempts == 0)
								c.connectionAttempts = connectionAttempts;
					}
				} catch (NumberFormatException nfe) {
