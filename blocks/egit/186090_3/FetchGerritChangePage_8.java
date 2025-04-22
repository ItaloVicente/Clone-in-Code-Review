		String defaultUri = null;
		CheckoutMode defaultCommand = CheckoutMode.CREATE_BRANCH;
		Change defaultChange = null;
		Matcher matcher = GERRIT_FETCH_PATTERN.matcher(initialText);
		if (matcher.matches()) {
			defaultUri = matcher.group(1);
			defaultChange = changeFromRef(matcher.group(2));
			String cmd = matcher.group(3);

			if ("checkout".equals(cmd)) { //$NON-NLS-1$
				defaultCommand = CheckoutMode.CHECKOUT_FETCH_HEAD;
			} else if ("cherry-pick".equals(cmd)) { //$NON-NLS-1$
				defaultCommand = CheckoutMode.CHERRY_PICK;
