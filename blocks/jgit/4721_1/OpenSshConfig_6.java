				if (current.isEmpty()) {
					continue;
				}

				if (StringUtils.equalsIgnoreCase("HostName"
					for (final Host c : current)
						if (c.hostName == null)
							c.hostName = dequote(argValue);
				} else if (StringUtils.equalsIgnoreCase("User"
					for (final Host c : current)
						if (c.user == null)
							c.user = dequote(argValue);
				} else if (StringUtils.equalsIgnoreCase("Port"
					try {
						final int port = Integer.parseInt(dequote(argValue));
						for (final Host c : current)
							if (c.port == 0)
								c.port = port;
					} catch (NumberFormatException nfe) {
					}
				} else if (StringUtils
						.equalsIgnoreCase("IdentityFile"
					for (final Host c : current)
						if (c.identityFile == null)
							c.identityFile = toFile(dequote(argValue));
				} else if (StringUtils.equalsIgnoreCase(
						"PreferredAuthentications"
					for (final Host c : current)
						if (c.preferredAuthentications == null)
							c.preferredAuthentications = nows(dequote(argValue));
				} else if (StringUtils.equalsIgnoreCase("BatchMode"
					for (final Host c : current)
						if (c.batchMode == null)
							c.batchMode = yesno(dequote(argValue));
				} else if (StringUtils.equalsIgnoreCase(
						"StrictHostKeyChecking"
					String value = dequote(argValue);
					for (final Host c : current)
						if (c.strictHostKeyChecking == null)
							c.strictHostKeyChecking = value;
				}
