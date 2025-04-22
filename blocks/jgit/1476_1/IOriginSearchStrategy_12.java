package org.eclipse.jgit.blame;

public interface IDifference {

	int getStartA();

	int getLengthA();

	int getStartB();

	int getLengthB();

}
