			if (editMaskParser.isComplete() && // should eventually be .isFull() to account for optional characters
				e.start == e.end &&
				e.text.length() > 0)
			{
				e.doit=false;
				return;
			}
