
				for (int i = 0; i < children.length / 2; i++) {
					Object temp = children[i];
					children[i] = children[children.length - i - 1];
					children[children.length - i - 1] = temp;
				}

