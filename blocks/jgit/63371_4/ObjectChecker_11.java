		if (emailE == emailB || raw[emailE - 1] != '>') {
			report(BAD_EMAIL
			bufPtr.value = nextLF(raw
			return;
		}
		if (emailE == raw.length || raw[emailE] != ' ') {
			report(MISSING_SPACE_BEFORE_DATE
					JGitText.get().corruptObjectBadDate);
			bufPtr.value = nextLF(raw
			return;
		}
