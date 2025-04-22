				} else if (c == '?') {
					int n = 1;
					for (; (i + 1) < sFilter.length(); i++) {
						if (sFilter.charAt(i + 1) != '?') {
							break;
						}
						n++;
					}
					sb.append(").").append(n == 1 ? '?' : String.format("{0,%d}", n)).append("("); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
