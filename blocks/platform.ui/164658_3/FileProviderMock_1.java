package org.eclipse.urischeme.win32.suite;

import org.eclipse.urischeme.internal.registration.win32.TestUnitRegistrationWindows;
import org.eclipse.urischeme.internal.registration.win32.TestUnitRegistryWriter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //
		TestUnitRegistrationWindows.class, //
		TestUnitRegistryWriter.class, //
})
public class AllUnitTests {
}
