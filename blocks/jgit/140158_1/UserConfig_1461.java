
package org.eclipse.jgit.lib;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.RefSpec;

public interface TypedConfigGetter {

	boolean getBoolean(Config config
			String name

	<T extends Enum<?>> T getEnum(Config config
			String subsection

	int getInt(Config config
			int defaultValue);

	long getLong(Config config
			long defaultValue);

	long getTimeUnit(Config config
			String name


	@NonNull
	List<RefSpec> getRefSpecs(Config config
			String name);
}
