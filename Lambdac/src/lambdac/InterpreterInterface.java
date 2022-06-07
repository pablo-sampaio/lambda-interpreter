package lambdac;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

import javax.swing.filechooser.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;


/**
 * Interface grafica do interpretador de lambdac.
 * 
 * @author Pablo Sampaio
 */
public class InterpreterInterface extends JFrame {
	private static final long serialVersionUID = 1L;

	private InterfaceController controller;

	protected Interpreter interpreter;

	protected JSplitPane splitPane;

	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();

	protected JLabel lblSourceCode = new JLabel("Código fonte: ");
	protected JLabel lblOuput       = new JLabel("Resultado:    ");
	
	protected JTextArea txaInput = new JTextArea();
	protected JTextArea txaOutput = new JTextArea();
	
	protected Color colorNeutral  = Color.LIGHT_GRAY;
	protected Color colorSuccess = new Color(135, 215, 150);
	protected Color colorFailure   = new Color(240, 140, 140);

	protected JToolBar toolBar = new JToolBar("Toolbar");
	protected JFileChooser fileChooser = new JFileChooser(new File("."));

	
	public InterpreterInterface() {
		controller = new InterfaceController(this);
		interpreter = new Interpreter();
		initFrame();
	}

	private void initFrame() {
		setTitle("Lambdac Interpreter (by Pablo 2014-2022)");
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.setPreferredSize(new Dimension(100, 150));
		panel1.setMinimumSize(new Dimension(100, 100));

		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel1.setPreferredSize(new Dimension(100, 100));
		panel2.setMinimumSize(new Dimension(100, 50));

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel1, panel2);
		splitPane.setDividerSize(4);
		splitPane.setResizeWeight(0.5);

		add(toolBar, BorderLayout.NORTH);
		add(splitPane, BorderLayout.CENTER);
		
		int fontSize = txaInput.getFont().getSize();
		txaInput.setFont(new Font("Courier New", Font.PLAIN, fontSize + 1));
		txaOutput.setFont(new Font("Courier New", Font.ITALIC, fontSize));
		
		JScrollPane scrollPane1 = new JScrollPane(txaInput, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel1.add(lblSourceCode);
		panel1.add(scrollPane1);

		txaOutput.setEditable(false);
		txaOutput.setBackground(colorNeutral);
		
		JScrollPane scrollPane2 = new JScrollPane(txaOutput, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel2.add(lblOuput);
		panel2.add(scrollPane2);
		
		fileChooser.setFileFilter(new XprFileFilter());
		
		populateToolBar();
	}
	
	private void populateToolBar() {
		ActionListener buttonListener;
		JButton button;
		
		// cria botão "Novo"
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonNewPressed(e);
			}
		}; 
		button = createButton("Novo", "Cria novo código fonte", buttonListener);
		toolBar.add(button);
		
		// cria botão "Abrir"
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonOpenPressed(e);
			}
		}; 
		button = createButton("Abrir", "Abre um arquivo", buttonListener);
		toolBar.add(button);
		
		// cria botão "Salvar"
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonSavePressed(e);
			}
		}; 
		button = createButton("Salvar", "Salva para arquivo", buttonListener);
		toolBar.add(button);
		
		toolBar.addSeparator();
		
		// cria botão "Compilar"
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonCompilePressed(e);
			}
		}; 
		button = createButton("Interpretar", "Interpreta o código fonte", buttonListener);
		toolBar.add(button);
		
		// cria botão "Limpar"
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buttonCleanPressed(e);
			}
		}; 
		button = createButton("Limpar", "Limpa a saída", buttonListener);
		toolBar.add(button);

		toolBar.addSeparator();
		
		// cria botão "Sair"
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}; 
		button = createButton("Sair", "Fecha a aplicação", buttonListener);
		toolBar.add(button);

	}
	
	private JButton createButton(String name, 
			                     String toolTipText,
			                     ActionListener listener) {

		String imgLocation = "..\\..\\images\\"
							+ name 
							+ ".png";
		URL imageURL = InterpreterInterface.class.getResource(imgLocation);
		
		//Create and initialize the button.
		JButton button = new JButton();
		button.setToolTipText(toolTipText);
		button.addActionListener(listener);
		
		if (imageURL != null) {
			button.setIcon(new ImageIcon(imageURL, name));
		} else {
			button.setText(name);
			System.err.println("Resource not found: " + imgLocation);
		}
		
		return button;
	}

	protected void clearOutput(boolean success) {
		txaOutput.setText("");
		if (success) {
			txaOutput.setBackground(colorSuccess);
		} else {
			txaOutput.setBackground(colorFailure);
		}
	}
	
	protected void clearOutput() {
		txaOutput.setText("");
		txaOutput.setBackground(colorNeutral);
	}
	
	protected void output(String msg) {
		txaOutput.append(msg);
	}
	
	public void showCenterScreen() {
		  Dimension dim = getToolkit().getScreenSize();
		  Rectangle abounds = getBounds();
		  setLocation((dim.width - abounds.width) / 2,
		      (dim.height - abounds.height) / 2);
		  setVisible(true);
		  requestFocus();
	}

}

class InterfaceController {
	private InterpreterInterface frame;

	InterfaceController(InterpreterInterface theFrame) {
		frame = theFrame;
		
	}

	void buttonNewPressed(ActionEvent e) {
		frame.txaInput.setText("");
		frame.clearOutput();
	}

	void buttonOpenPressed(ActionEvent e) {
		int option = frame.fileChooser.showOpenDialog(frame);
		
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = frame.fileChooser.getSelectedFile();
			
			try { 
				BufferedReader fileReader = new BufferedReader(new FileReader(file));
				StringBuilder fileContent = new StringBuilder();
			
				String line = fileReader.readLine();
				
				while (line != null) {
					fileContent.append(line.replaceAll("\t", "    "));
					fileContent.append('\n');
					line = fileReader.readLine();
				}

				frame.txaInput.setText(fileContent.toString());
				frame.clearOutput();
			
			} catch (IOException exc) {
				frame.clearOutput(false);
				frame.output(exc.getMessage());
			}

		}

	}

	void buttonSavePressed(ActionEvent e) {
		int option = frame.fileChooser.showSaveDialog(frame);
		
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = frame.fileChooser.getSelectedFile();

			String text = frame.txaInput.getText().replace("\n","\r\n");

			try { 
				FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), false);
				fileWriter.write(text);
				fileWriter.close();
						
			} catch (IOException exc) {
				frame.clearOutput(false);
				frame.output(exc.getMessage());
			}
		}
	}

	void buttonCompilePressed(ActionEvent e) {
		String codigo = frame.txaInput.getText();
		
		ErrorReport mensagensErro;

		frame.clearOutput(true);
		
		try {
			mensagensErro = frame.interpreter.interpret(new ByteArrayInputStream(codigo.getBytes()),
					new TextAreaOutputStream(frame.txaOutput));
		
		} catch (Exception exc) { //TODO: rever
			mensagensErro = new ErrorReport();
			mensagensErro.addMessage(exc.getMessage() + " - " + exc.getLocalizedMessage());

		}
		
		if (mensagensErro.getNumMessages() > 0) {
			frame.clearOutput(false);
			
			for (String msg : mensagensErro.getAllMessages()) {
				frame.output(msg + "\n");			
			}
		}

	}
	
	void buttonCleanPressed(ActionEvent e) {
		frame.clearOutput();
	}

}

class XprFileFilter extends FileFilter {

	@Override
	public boolean accept(File path) {
		if (path.isDirectory()) {
			return true;
		}
		
		String pathName = path.getName();
		int lastDot = pathName.lastIndexOf('.');
		
		String extension = "";
		
		if (lastDot > 0 &&  lastDot < (pathName.length()-1)) {
			extension = pathName.substring(lastDot + 1);
		}
		
		return extension.equalsIgnoreCase("LAM");
	}

	@Override
	public String getDescription() {
		return "Lambdac source files (*.lam)";
	}
	
};
