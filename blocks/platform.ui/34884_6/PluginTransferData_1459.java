package org.eclipse.ui.part;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

public class PluginTransfer extends ByteArrayTransfer {

    private static final String TYPE_NAME = "pluggable-transfer-format";//$NON-NLS-1$

    private static final int TYPEID = registerType(TYPE_NAME);

    private static PluginTransfer instance = new PluginTransfer();

    private PluginTransfer() {
        super();
    }

    public static PluginTransfer getInstance() {
        return instance;
    }

    @Override
	protected int[] getTypeIds() {
        return new int[] { TYPEID };
    }

    @Override
	protected String[] getTypeNames() {
        return new String[] { TYPE_NAME };
    }

    @Override
	protected void javaToNative(Object data, TransferData transferData) {
        PluginTransferData realData = (PluginTransferData) data;
        if (data == null) {
			return;
		}
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeUTF(realData.getExtensionId());
            dataOut.writeInt(realData.getData().length);
            dataOut.write(realData.getData());
            dataOut.close();
            super.javaToNative(out.toByteArray(), transferData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
	protected Object nativeToJava(TransferData transferData) {
        try {
            byte[] bytes = (byte[]) super.nativeToJava(transferData);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            DataInputStream dataIn = new DataInputStream(in);
            String extensionName = dataIn.readUTF();
            int len = dataIn.readInt();
            byte[] pluginData = new byte[len];
            dataIn.readFully(pluginData);
            return new PluginTransferData(extensionName, pluginData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
