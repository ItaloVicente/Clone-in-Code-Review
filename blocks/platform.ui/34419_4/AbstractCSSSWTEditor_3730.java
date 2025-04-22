package org.akrogen.tkui.css.swt.examples.chat;

import org.akrogen.tkui.css.swt.resources.CSSSWTResources;

public class ChatWithVistaStyle extends AbstractChatExample {

	public ChatWithVistaStyle() {
		super(CSSSWTResources.getSWTVista());
	}

	public static void main(String[] args) {
		ChatWithVistaStyle testVista = new ChatWithVistaStyle();
		try {
			testVista.display();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
