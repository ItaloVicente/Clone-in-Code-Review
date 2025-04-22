		final int emailB = nextLF(raw
		if (emailB == ptr.value || raw[emailB - 1] != '<') {
			report(MISSING_EMAIL
			ptr.value = nextLF(raw
			return;
		}
