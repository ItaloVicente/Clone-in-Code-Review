			Collections.sort(refs, new Comparator<String>() {
				public int compare(String o1, String o2) {
					int charCompare = o1Chars.compareTo(o2Chars);

					if (charCompare == 0) {
						if (o1Numbers.length() == 0)
						if (o2Numbers.length() == 0)

						return Integer.parseInt(o2Numbers)
								- Integer.parseInt(o1Numbers);
					}

					return charCompare;
				}
			});
