			words = new Word[wordsSplitted.length + 1];
			words[wordsIndex] = new Word();
			words[wordsIndex].pattern = trimedPattern;
			wordsIndex = 1;
		} else {
			words = new Word[wordsSplitted.length];
		}
		for (int i = 0; i < wordsSplitted.length; i++) {
			words[wordsIndex] = new Word();
			words[wordsIndex].pattern = wordsSplitted[i];
			wordsIndex++;
