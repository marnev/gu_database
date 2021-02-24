package io.gu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

	public Gui() {
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setSize(750, 250); // 750 / 500
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		frame.setTitle("GoalUnited");
		
		panel.setLayout(null);
		
		// Labels + Textfields
		JLabel sourceLabel = new JLabel("Datei:");
		sourceLabel.setBounds(20, 40, 80, 30);
		
		JTextField sourceText = new JTextField(80);
		sourceText.setBounds(75, 40, 620, 30);
		
		JTextField inputText = new JTextField(120);
		inputText.setBounds(75, 180, 620, 20);
		
		JButton button_readSql = new JButton("Datei einlesen");
		button_readSql.setBounds(290, 105, 150, 30);
		
		JButton button_createCsv = new JButton("CSV erstellen");
		button_createCsv.setBounds(290, 150, 150, 30);
		
		button_readSql.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if (sourceText.getText().length() > 0) {
				try {
				
					new MyCSVReader(sourceText.getText());
					JOptionPane.showMessageDialog(null, "Daten erfolgreich eingelesen!", "Hinweis", JOptionPane.INFORMATION_MESSAGE, null);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Bitte gültigen Pfad angeben!", "Hinweis", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
		});
		
		button_createCsv.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			//mcr.setCreateFile(true);
		}
		});
		
		panel.add(sourceLabel);
		panel.add(sourceText);
		//panel.add(inputText);
		panel.add(button_readSql);
		panel.add(button_createCsv);
		
	}
	
}
