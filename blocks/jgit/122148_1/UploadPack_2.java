			} else if (transferConfig.isAllowFilter()
				String arg = line.substring(OPTION_FILTER.length() + 1);

				if (filterReceived) {
					throw new PackProtocolException(JGitText.get().tooManyFilters);
				}
				filterReceived = true;

				parseFilter(arg);
				continue;
