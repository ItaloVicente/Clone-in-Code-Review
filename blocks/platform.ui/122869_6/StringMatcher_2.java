			this.wholePatternWord = new Word(trimedPattern);
			patternWords = trimedPattern.split("\\s+"); //$NON-NLS-1$
			if (patternWords.length > 1) {
				this.splittedPatternWords = new Word[patternWords.length];
				for (int i = 0; i < patternWords.length; i++) {
					String patternWord = patternWords[i];
					if (!patternWord.endsWith("*")) { //$NON-NLS-1$
						patternWord += '*';
					}
					this.splittedPatternWords[i] = new Word(patternWord);
				}
			}
