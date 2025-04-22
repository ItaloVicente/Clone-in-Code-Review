
package com.couchbase.client.core.encryption;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.utils.Base64;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.nio.charset.Charset;

@InterfaceStability.Uncommitted
public class HashicorpVaultKeyStoreProvider implements KeyStoreProvider {

    private final String endpoint;
    private final String mount;
    private final String token;
    private ObjectMapper mapper = new ObjectMapper();

    public HashicorpVaultKeyStoreProvider(String endpoint, String token) {
        this(endpoint, "secret", token);
    }

    public HashicorpVaultKeyStoreProvider(String endpoint, String mount, String token) {
        this.endpoint = endpoint;
        this.mount = mount;
        this.token = token;
    }

    @Override
    public byte[] getKey(String keyName) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(this.endpoint + "/v1/" + this.mount + "/" + keyName);
        get.addHeader("X-Vault-Token", this.token);

        CloseableHttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("Store key failed on vault " + response.getStatusLine().toString());
        }
        String storedVal = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        JsonNode obj = mapper.readTree(storedVal);
        client.close();
        return Base64.decode(obj.get("data").get("value").toString().replace("\"",""));
    }

    @Override
    public void storeKey(String keyName, byte[] key) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(this.endpoint + "/v1/" + this.mount + "/" + keyName);
        post.addHeader("X-Vault-Token",this.token);
        ObjectNode node = mapper.createObjectNode();
        node.put("value" , Base64.encode(key));
        post.setEntity(new StringEntity(node.toString(), ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
            throw new Exception("Store key failed on vault " + response.getStatusLine().toString());
        }
        client.close();
    }

    @Override
    public void storeKey(String keyName, byte[] publicKey, byte[] privateKey) throws Exception {
        this.storeKey(keyName + "_public", publicKey);
        this.storeKey(keyName + "_private", privateKey);
    }
}
