			bufPtr.value = nextLF(raw
			return false;
		}

		p += OBJECT_ID_STRING_LENGTH;
		if (raw[p] == '\n') {
			bufPtr.value = p + 1;
			return true;
