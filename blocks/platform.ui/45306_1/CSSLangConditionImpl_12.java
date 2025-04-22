		String s = e.getAttribute("lang").toLowerCase();
		if (s.equals(lang) || s.startsWith(langHyphen)) {
			return true;
		}
		return s.equals(lang) || s.startsWith(langHyphen);
	}

	@Override
	public void fillAttributeSet(Set<String> attrSet) {
		attrSet.add("lang");
	}

	@Override
