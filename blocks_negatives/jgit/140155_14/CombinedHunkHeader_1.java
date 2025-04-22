		for (int n = 0; n < old.length; n++) {
			old[n].startLine = -parseBase10(buf, ptr.value, ptr);
			if (buf[ptr.value] == ',')
				old[n].lineCount = parseBase10(buf, ptr.value + 1, ptr);
			else
				old[n].lineCount = 1;
