			if (getScheme() != null)
				if (escapeNonAscii)
					r.append(escape(getPath()
				else
					r.append(getRawPath());
			else
				r.append(getPath());
