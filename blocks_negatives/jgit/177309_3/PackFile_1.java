			base = name;
			hasOldPrefix = false;
			packExt = null;
		} else {
			base = name.substring(0, dot);
			packExt = getPackExt(tail);
			String old = tail.substring(0,
					tail.length() - getExtension().length());
			hasOldPrefix = old.equals(getExtPrefix(true));
