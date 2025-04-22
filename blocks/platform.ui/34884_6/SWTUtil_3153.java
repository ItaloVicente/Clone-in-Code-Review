
package org.eclipse.jface.examples.databinding.mask.internal;

import java.util.LinkedList;

import org.eclipse.jface.examples.databinding.mask.EditMaskParseException;

public class EditMaskParser {
	private EditMaskLexerAndToken[] expectedTokens;
	private char placeholder = ' ';

	public EditMaskParser(String editMask) throws EditMaskParseException {
		LinkedList tokens = new LinkedList();
		int position = 0;
		while (position < editMask.length()) {
			EditMaskLexerAndToken token = new EditMaskLexerAndToken();
			position += token.initializeEditMask(editMask, position);
			tokens.add(token);
		}
		expectedTokens = (EditMaskLexerAndToken[]) tokens.toArray(new EditMaskLexerAndToken[tokens.size()]);
	}

	public void setInput(String input) {
		for (int i = 0; i < expectedTokens.length; i++) {
			expectedTokens[i].clear();
		}
		int tokenPosition = 0;
		int inputPosition = 0;
		while (inputPosition < input.length() && tokenPosition < expectedTokens.length) {
			while (tokenPosition < expectedTokens.length &&
					(expectedTokens[tokenPosition].isComplete() ||
					 expectedTokens[tokenPosition].isReadOnly()))
			{
				++tokenPosition;
			}
			if (tokenPosition < expectedTokens.length) {
				while (inputPosition < input.length() && !expectedTokens[tokenPosition].isComplete()) {
					String inputChar = input.substring(inputPosition, inputPosition+1);
					expectedTokens[tokenPosition].accept(inputChar);
					++inputPosition;
				}
			}
		}
	}

	public String getFormattedResult() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < expectedTokens.length; i++) {
			String outputChar = expectedTokens[i].getInput();
			if (outputChar == null) {
				outputChar = "" + placeholder;
			}
			result.append(outputChar);
		}
		return result.toString();
	}

	public String getRawResult() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < expectedTokens.length; i++) {
			if (expectedTokens[i].isReadOnly()) {
				continue;
			}
			String outputChar = expectedTokens[i].getInput();
			if (outputChar == null) {
				outputChar = "";
			}
			result.append(outputChar);
		}
		return result.toString();
	}

	public boolean isComplete() {
		for (int i = 0; i < expectedTokens.length; i++) {
			if (!expectedTokens[i].isComplete()) {
				return false;
			}
		}
		return true;
	}

	public int getNextInputPosition(int startingAt) {
		while (startingAt < expectedTokens.length-1 && expectedTokens[startingAt].isReadOnly()) {
			++startingAt;
		}
		return startingAt;
	}

	public int getFirstIncompleteInputPosition() {
		for (int position = 0; position < expectedTokens.length; position++) {
			if (!expectedTokens[position].isComplete()) {
				return position;
			}
		}
		return -1;
	}

	public char getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(char placeholder) {
		this.placeholder = placeholder;
	}
}
