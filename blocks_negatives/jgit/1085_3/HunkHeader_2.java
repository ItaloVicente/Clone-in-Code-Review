				oLine++;
				in.extendA();
				continue;

			case '+':
				if (in == null) {
					in = new Edit(oLine - 1, nLine - 1);
					r.add(in);
				}
				nLine++;
				in.extendB();
				continue;

				continue;

			default:
				break SCAN;
