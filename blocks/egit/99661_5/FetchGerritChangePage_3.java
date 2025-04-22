			matcher = GERRIT_CHANGE_REF_PATTERN.matcher(input);
			if (matcher.matches()) {
				int firstNum = Integer.parseInt(matcher.group(1));
				int secondNum = Integer.parseInt(matcher.group(2));
				return Change.create(firstNum, secondNum);
			}
		} catch (NumberFormatException e) {
