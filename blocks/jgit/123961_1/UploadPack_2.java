			} else if (transferConfig.isAllowFilter()
					&& line.startsWith(OPTION_FILTER + ' ')) {
				if (filterReceived) {
					throw new PackProtocolException(JGitText.get().tooManyFilters);
				}
				filterReceived = true;
				parseFilter(line.substring(OPTION_FILTER.length() + 1));
