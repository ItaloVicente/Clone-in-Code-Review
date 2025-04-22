					for (String capability : line.substring(nul + 1)
						if (capability.startsWith(CAPABILITY_SYMREF_PREFIX)) {
							String[] parts = capability
									.substring(
											CAPABILITY_SYMREF_PREFIX.length())
									.split(":"
							if (parts.length == 2) {
								symRefs.put(parts[0]
							}
						} else {
							addCapability(capability);
						}
					}
