package org.eclipse.egit.ui.internal;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class CommonUtilsTest {
	@Test
	public void sortingNumbersBiggerThanIntegerShouldNotResultInNumberFormatException() {
		List<String> refs = Arrays.asList("refs/tags/stable-1-0-0_2011-05-25", "refs/tags/stable-1-0-0_2011-01-01");
		Collections.sort(refs, CommonUtils.STRING_ASCENDING_COMPARATOR);
		assertEquals(Arrays.asList("refs/tags/stable-1-0-0_2011-01-01", "refs/tags/stable-1-0-0_2011-05-25"), refs);
	}
}
