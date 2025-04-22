public class PatienceDiffTest extends AbstractDiffTestCase {
	@Override
	protected DiffAlgorithm algorithm() {
		PatienceDiff pd = new PatienceDiff();
		pd.setFallbackAlgorithm(null);
		return pd;
