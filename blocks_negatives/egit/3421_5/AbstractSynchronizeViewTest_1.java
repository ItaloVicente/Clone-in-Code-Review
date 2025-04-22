		if (!includeLocal && srcRepo != null)
			bot.comboBox(0)
					.setSelection(srcRepo);
		if (!includeLocal && srcRef != null)
			bot.comboBox(1).setSelection(srcRef);

		if (dstRepo != null)
			bot.comboBox(2)
					.setSelection(dstRepo);
		if (dstRef != null)
			bot.comboBox(3).setSelection(dstRef);
