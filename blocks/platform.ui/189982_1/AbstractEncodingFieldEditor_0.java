
			HashSet<Charset> remainingCharsets = new HashSet<>();
			remainingCharsets.addAll(Charset.availableCharsets().values());

			for (String predefinedEncoding : encodings) {
				Charset predefinedCharset = null;
				try {
					predefinedCharset = Charset.forName(predefinedEncoding);
				} catch (UnsupportedCharsetException unsupportedCharsetException) {
				}
				if (predefinedCharset != null) {
					remainingCharsets.remove(predefinedCharset);
				}
			}

			ArrayList<String> remainingCharsetNamesSorted = new ArrayList<>();
			for (Charset remainingCharset : remainingCharsets) {
				remainingCharsetNamesSorted.add(remainingCharset.name());
			}

			remainingCharsetNamesSorted.sort(null);

			encodings.addAll(remainingCharsetNamesSorted);

