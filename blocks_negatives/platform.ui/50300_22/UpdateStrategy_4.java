				map
						.put(new Pair(fromName, LONG_CLASS),
								new NumberToLongConverter(numberFormat,
										fromType, true));
				map
						.put(new Pair(fromName, Long.class.getName()),
								new NumberToLongConverter(numberFormat,
										fromType, false));
