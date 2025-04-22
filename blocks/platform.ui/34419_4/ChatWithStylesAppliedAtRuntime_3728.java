package org.akrogen.tkui.css.swt.examples.chat;

import org.akrogen.tkui.css.swt.resources.CSSSWTResources;

public class ChatWithOsxStyle extends AbstractChatExample {

	public ChatWithOsxStyle() {
		super(CSSSWTResources.getSWTOsx());
	}

	public static void main(String[] args) {
		ChatWithOsxStyle testOsx = new ChatWithOsxStyle();
		try {
			testOsx.display();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
