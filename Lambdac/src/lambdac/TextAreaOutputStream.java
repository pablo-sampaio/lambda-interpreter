package lambdac;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {
	private byte[] buffer = new byte[2];
	private boolean hasOne;
	
	private JTextArea textArea;
	
	TextAreaOutputStream(JTextArea txa) {
		textArea = txa;
	}

	@Override
	public void write(int data) throws IOException {

		if (hasOne == false) {
			buffer[0] = (byte)data;
			hasOne = true;
		} else {
			buffer[1] = (byte)data;
			textArea.setText(textArea.getText() + (new String(buffer)));
			hasOne = false;
		}
		
	}

}
