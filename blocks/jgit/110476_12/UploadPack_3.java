			if (transferConfig.isAllowFilter()
				String arg = line.substring(OPTION_FILTER.length() + 1);

				if (filterReceived) {
					throw new PackProtocolException(JGitText.get().tooManyFilters);
				}
				filterReceived = true;

				if (arg.equals("blob:none")) {
					filterBlobLimit = 0;
				} else if (arg.startsWith("blob:limit=")) {
					try {
						filterBlobLimit = Long.parseLong(arg.substring("blob:limit=".length()));
					} catch (NumberFormatException e) {
						throw new PackProtocolException(
								MessageFormat.format(JGitText.get().invalidFilter
										arg));
					}
				}
				if (filterBlobLimit < 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidFilter
									arg));
				}
				continue;
			}

