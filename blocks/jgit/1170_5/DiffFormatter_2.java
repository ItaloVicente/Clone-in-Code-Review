		case MODIFY:
			int score = ent.getScore();
			if (score > 0 && score <= 100) {
				o.write(encodeASCII("dissimilarity index " + (100 - score)
						+ "%"));
				o.write('\n');
			}
			break;
