		ignore = EnumSet.noneOf(ObjectChecker.ErrorType.class);
		EnumSet<ObjectChecker.ErrorType> set = EnumSet
				.noneOf(ObjectChecker.ErrorType.class);
		for (String key : rc.getNames(FSCK)) {
			if (equalsIgnoreCase(key
					|| equalsIgnoreCase(key
					|| equalsIgnoreCase(key
					|| equalsIgnoreCase(key
					|| equalsIgnoreCase(key
				continue;
			}

			ObjectChecker.ErrorType id = FsckKeyNameHolder.parse(key);
			if (id != null) {
				switch (rc.getEnum(FSCK
				case ERROR:
					ignore.remove(id);
					break;
				case WARN:
				case IGNORE:
					ignore.add(id);
					break;
				}
				set.add(id);
			}
		}
		if (!set.contains(ObjectChecker.ErrorType.ZERO_PADDED_FILEMODE)
				&& rc.getBoolean(FSCK
			ignore.add(ObjectChecker.ErrorType.ZERO_PADDED_FILEMODE);
		}

