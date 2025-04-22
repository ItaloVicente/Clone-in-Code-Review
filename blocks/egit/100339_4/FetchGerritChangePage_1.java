				int firstNum = Integer.parseInt(matcher.group(2));
				String second = matcher.group(3);
				if (second != null) {
					return Change.create(firstNum, Integer.parseInt(second));
				} else {
					return Change.create(firstNum);
				}
