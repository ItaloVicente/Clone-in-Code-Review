		final int emailB = nextLF(raw
		if (emailB == bufPtr.value || raw[emailB - 1] != '<') {
			report(MISSING_EMAIL
			bufPtr.value = nextLF(raw
			return;
		}
