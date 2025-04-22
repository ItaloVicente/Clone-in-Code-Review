
		p += OBJECT_ID_STRING_LENGTH;
		if (raw[p] == '\n') {
			bufPtr.value = p + 1;
			return true;
		}
		bufPtr.value = nextLF(raw
		return false;
