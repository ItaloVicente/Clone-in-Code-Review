package org.akrogen.tkui.css.swt.examples.chat;

import org.akrogen.tkui.css.swt.resources.CSSSWTResources;


public class ChatWithMatrixStyle extends AbstractChatExample {

	public ChatWithMatrixStyle() {
		super(CSSSWTResources.getSWTMatrix());
	}

	public static void main(String[] args) {
		ChatWithMatrixStyle testMatrix = new ChatWithMatrixStyle();
		try {
			testMatrix.display();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
