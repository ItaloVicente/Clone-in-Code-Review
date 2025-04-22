				String input = contents;
				Matcher matcher = GERRIT_CHANGE_REF_PATTERN.matcher(contents);
				if (matcher.find()) {
					input = matcher.group(1);
				}
				Pattern pattern = UIUtils.createProposalPattern(input);
