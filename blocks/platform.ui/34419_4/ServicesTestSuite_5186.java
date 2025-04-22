
package org.eclipse.ui.tests.services;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

public class LevelServiceFactory extends AbstractServiceFactory {

	static int instancesCreated = 0;

	private static class LS implements ILevelService {
		private int level;

		public LS(int l) {
			level = l;
		}

		@Override
		public int getLevel() {
			return level;
		}

	};

	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		int level = 1;
		Object parent = parentLocator.getService(ILevelService.class);
		if (parent != null) {
			ILevelService l = (ILevelService) parent;
			level = l.getLevel() + 1;
		}
		instancesCreated++;
		return new LS(level);
	}

}
