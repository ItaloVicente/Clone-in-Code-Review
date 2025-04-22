package org.eclipse.jgit.niofs.fs.attribute;

import java.util.List;

public interface FileDiff {

	List<String> getLinesA();

	List<String> getLinesB();

	String getChangeType();

	String getNameA();

	String getNameB();

	int getStartA();

	int getEndA();

	int getStartB();

	int getEndB();
}
