			if (b.length() > 2)
				b.setLength(b.length() - 2);
			String multiResourcePrefix = NLS.bind(
					UIText.GitHistoryPage_MultiResourcesType, Integer
							.valueOf(in.getItems().length));
			return NLS.bind(NAME_PATTERN, new Object[] { multiResourcePrefix,
					b.toString(), repositoryName });
