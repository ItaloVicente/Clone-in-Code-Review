		case MODIFY:
			int score = ent.getScore();
			if (0 < score && score <= 100) {
				o.write(encodeASCII("dissimilarity index " + (100 - score)
						+ "%"));
				o.write('\n');
			}
			break;
