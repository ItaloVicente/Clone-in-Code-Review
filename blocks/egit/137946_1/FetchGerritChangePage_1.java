				input = matcher.group(3);
				if (input != null) {
					return UIUtils.createProposalPattern(input);
				}
				input = matcher.group(1);
				if (input != null) {
					if (input.charAt(0) == '0') {
						input = input.substring(1);
					}
					return UIUtils.createProposalPattern(input);
				}
			}
			matcher = DIGITS.matcher(input);
			if (matcher.matches()) {
				input = matcher.group(1);
				return UIUtils.createProposalPattern(input);
