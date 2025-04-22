
package org.eclipse.ui.part;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

public class EditorInputTransfer extends ByteArrayTransfer {

    private static final EditorInputTransfer instance = new EditorInputTransfer();

    private static final String TYPE_NAME = "editor-input-transfer-format:" + System.currentTimeMillis() + ":" + instance.hashCode(); //$NON-NLS-2$//$NON-NLS-1$

    private static final int TYPEID = registerType(TYPE_NAME);

    public static class EditorInputData {

        public String editorId;

        public IEditorInput input;

        private EditorInputData(String editorId, IEditorInput input) {
            this.editorId = editorId;
            this.input = input;
        }
    }

    private EditorInputTransfer() {
    }

    public static EditorInputTransfer getInstance() {
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
	public void javaToNative(Object data, TransferData transferData) {

        if (!(data instanceof EditorInputData[])) {
            return;
        }

        EditorInputData[] editorInputs = (EditorInputData[]) data;

        int editorInputCount = editorInputs.length;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);

            dataOut.writeInt(editorInputCount);

            for (int i = 0; i < editorInputs.length; i++) {
                writeEditorInput(dataOut, editorInputs[i]);
            }

            dataOut.close();
            out.close();
            byte[] bytes = out.toByteArray();
            super.javaToNative(bytes, transferData);
        } catch (IOException e) {
        }
    }

    @Override
	public Object nativeToJava(TransferData transferData) {

        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        if (bytes == null) {
			return null;
		}
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(bytes));
        try {
            int count = in.readInt();
            EditorInputData[] results = new EditorInputData[count];
            for (int i = 0; i < count; i++) {
                results[i] = readEditorInput(in);
            }
            return results;
        } catch (IOException e) {
            return null;
        } catch (WorkbenchException e) {
            return null;
        }

    }

    private EditorInputData readEditorInput(DataInputStream dataIn)
            throws IOException, WorkbenchException {

        String editorId = dataIn.readUTF();
        String factoryId = dataIn.readUTF();
        String xmlString = dataIn.readUTF();

        if (xmlString == null || xmlString.length() == 0) {
			return null;
		}

        StringReader reader = new StringReader(xmlString);

        XMLMemento memento = XMLMemento.createReadRoot(reader);

        IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryId);

        if (factory != null) {
            IAdaptable adaptable = factory.createElement(memento);
            if (adaptable != null && (adaptable instanceof IEditorInput)) {
                return new EditorInputData(editorId, (IEditorInput) adaptable);
            }
        }

        return null;
    }

    private void writeEditorInput(DataOutputStream dataOut,
            EditorInputData editorInputData) throws IOException {
        dataOut.writeUTF(editorInputData.editorId);

        if (editorInputData.input != null) {
            XMLMemento memento = XMLMemento.createWriteRoot("IEditorInput");//$NON-NLS-1$

            IPersistableElement element = editorInputData.input
                    .getPersistable();
            if (element != null) {
                element.saveState(memento);

                StringWriter writer = new StringWriter();
                memento.save(writer);
                writer.close();

                dataOut.writeUTF(element.getFactoryId());
                dataOut.writeUTF(writer.toString());
            }
        }
    }

    public static EditorInputData createEditorInputData(String editorId,
            IEditorInput input) {
        return new EditorInputData(editorId, input);
    }

}
