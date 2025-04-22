package org.eclipse.ui.part;

public class PluginTransferData {
    String extensionName;

    byte[] transferData;

    public PluginTransferData(String extensionId, byte[] data) {
        this.extensionName = extensionId;
        this.transferData = data;
    }

    public byte[] getData() {
        return transferData;
    }

    public String getExtensionId() {
        return extensionName;
    }
}
