				map
						.put(new Pair(fromName, BYTE_CLASS),
								new NumberToByteConverter(numberFormat,
										fromType, true));
				map
						.put(new Pair(fromName, Byte.class.getName()),
								new NumberToByteConverter(numberFormat,
										fromType, false));
