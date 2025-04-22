			for (; (ptr + 2) < p; p--) {
				if (raw[p] == '.')
					dots++;
				else if (raw[p] == ' ')
					space = true;
				else
					break;
			}
