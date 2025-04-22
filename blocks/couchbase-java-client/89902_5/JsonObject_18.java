
    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public class EncryptionInfo {
        private String type;
        private String crypto_provider;

        public EncryptionInfo(String type, String crypto_provider) {
            this.type = type;
            this.crypto_provider = crypto_provider;
        }

        public String type() {
            return this.type;
        }

        public String crypto_provider() {
            return this.crypto_provider;
        }
    }
