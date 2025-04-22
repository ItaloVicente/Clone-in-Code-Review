package org.eclipse.e4.demo.cssbridge.core;

import java.util.List;

import org.eclipse.e4.demo.cssbridge.model.FolderType;
import org.eclipse.e4.demo.cssbridge.model.Mail;

public interface IMailService {
	String getMailboxName();

	List<Mail> getMails(FolderType type);
}
