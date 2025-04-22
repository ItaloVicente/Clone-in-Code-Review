package org.eclipse.jgit.blame;

public interface IOriginSearchStrategy {

	Origin[] findOrigins(Origin source);
}
