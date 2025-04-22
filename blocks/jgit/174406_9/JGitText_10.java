package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class WrongObjectTypeException extends GitAPIException {

	private static final long serialVersionUID = 1L;

	private String name;

	private int type;

	public WrongObjectTypeException(ObjectId id
		super(MessageFormat.format(JGitText.get().objectIsNotA
				Constants.typeString(type)));
		this.name = id.name();
		this.type = type;
	}

	public String getObjectId() {
		return name;
	}

	public int getExpectedType() {
		return type;
	}
}
