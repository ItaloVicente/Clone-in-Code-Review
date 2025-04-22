				return always
						? w.getObjectReader()
								.abbreviate(target
										AbbrevConfig.capAbbrev(abbrev))
								.name()
						: null;
