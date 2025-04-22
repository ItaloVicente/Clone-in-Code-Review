	private List<String> getSubSections(Config configuration, String key) {

		List<String> result = new ArrayList<String>();

			result.add(key);
			return result;
		}

		StringTokenizer stok = new StringTokenizer(key, "."); //$NON-NLS-1$
		if (stok.countTokens() == 3) {
			String section = stok.nextToken();
			String subsection = stok.nextToken();
			String name = stok.nextToken();
				Set<String> subs = configuration.getSubsections(section);
				for (String sub : subs)
				return result;
			} else {
				result.add(key);
			}
		}
		return result;
	}

