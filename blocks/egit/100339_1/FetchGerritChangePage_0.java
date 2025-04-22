				String second = matcher.group(2);
				if (second != null) {
					return Change.create(firstNum, Integer.parseInt(second));
				} else {
					return Change.create(firstNum);
				}
