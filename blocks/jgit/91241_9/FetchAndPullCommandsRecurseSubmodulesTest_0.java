import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class FetchAndPullCommandsRecurseSubmodulesTest extends RepositoryTestCase {
	@DataPoints
	public static boolean[] useFetch = { true
