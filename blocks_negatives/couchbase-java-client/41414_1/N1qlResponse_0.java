import java.util.ArrayList;
import java.util.List;

public class N1qlResponse {
    private int resultCount = 0;
    private List<JSONObject> results;

    protected N1qlResponse() {
    }

    public int getResultCount() {
        return resultCount;
    }

    public List<JSONObject> getResults() {
        return results;
    }

    protected N1qlResponse parseJson(JSONObject json) throws JSONException {
        JSONArray jsonResults = json.getJSONArray("resultset");
        resultCount = jsonResults.length();
        results = new ArrayList<JSONObject>(resultCount);
        for(int i = 0; i < resultCount; ++i) {
            JSONObject jsonResult = jsonResults.getJSONObject(i);
            results.add(jsonResult);
        }
        return this;
    }
