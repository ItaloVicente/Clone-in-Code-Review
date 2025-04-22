			if (refName == null) {
				return null;
			}
			Matcher m = GERRIT_CHANGE_REF_PATTERN.matcher(refName);
			if (!m.matches() || m.group(3) == null) {
				return null;
			}
			Integer subdir = Integer.valueOf(m.group(1));
			int changeNumber = Integer.parseInt(m.group(2));
			if (subdir.intValue() != changeNumber % 100) {
				return null;
			}
			Integer patchSetNumber = Integer.valueOf(m.group(3));
			return new GerritChange(refName, changeNumber, patchSetNumber);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return null;
