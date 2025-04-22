		static Change fromRef(String refName) {
			try {
				if (refName == null) {
					return null;
				}
				Matcher m = GERRIT_CHANGE_REF_PATTERN.matcher(refName);
				if (!m.matches() || m.group(3) == null) {
					return null;
				}
				Integer subdir = Integer.valueOf(m.group(1));
				Integer changeNumber = Integer.valueOf(m.group(2));
				if (subdir.intValue() != changeNumber.intValue() % 100) {
					return null;
				}
				Integer patchSetNumber = Integer.valueOf(m.group(3));
				return new Change(refName, changeNumber, patchSetNumber);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				return null;
			}
		}

		static Change create(int changeNumber) {
			return new Change(null, Integer.valueOf(changeNumber), null);
