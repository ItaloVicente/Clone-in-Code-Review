
	public RefFilter getRefFilter() {
		if (hideRefs.length == 0)
			return RefFilter.DEFAULT;

		return new RefFilter() {
			public Map<String
				Map<String
				for (Map.Entry<String
					boolean add = true;
					for (String hide : hideRefs) {
						if (e.getKey().equals(hide) || prefixMatch(hide
							add = false;
							break;
						}
					}
					if (add)
						result.put(e.getKey()
				}
				return result;
			}

			private boolean prefixMatch(String p
				return p.charAt(p.length() - 1) == '/' && s.startsWith(p);
			}
		};
	}
