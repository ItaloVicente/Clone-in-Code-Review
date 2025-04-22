			if (b.length() > 2)
				b.setLength(b.length() - 2);
			String multiResourcePrefix = NLS.bind(
					UIText.GitHistoryPage_MultiResourcesType, Integer
							.valueOf(count));
			return NLS.bind(NAME_PATTERN, new Object[] { multiResourcePrefix,
					b.toString(), repositoryName });
