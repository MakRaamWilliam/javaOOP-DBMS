package eg.edu.alexu.csd.oop.db;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import javax.swing.JSeparator;
import java.awt.List;
import java.awt.Panel;

public class UI {
	GeneralDBMS ob2=new GeneralDBMS();
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 562, 406);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 310, 355, 52);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		JLabel lblNewLabel = new JLabel("");
		//frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel.setForeground(new Color(178, 34, 34));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setLabelFor(frame);
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setBounds(21, 21, 517, 274);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(lblNewLabel);
		scrollPane.setBounds(10, 10, 528, 294);
		frame.getContentPane().add(scrollPane);
		JButton btnNewButton = new JButton("Execute");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query=textField.getText();
				Query ob1=new Query();
				String function=ob1.call(query);
				if(function==null) {
					lblNewLabel.setText("Not Valid input");
				}
				else {
					if(function.equals("update")) {
						int result;
						try {
							result = ob2.executeUpdateQuery(query);
						
							if(result!=1) {
								lblNewLabel.setText(result+" rows are updated");
							}
							else {
								lblNewLabel.setText(result+" row is updated");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else if(function.equals("execute")) {
						try {
							Object arr[][]=ob2.executeQuery(query);
							lblNewLabel.setText(" ");
							lblNewLabel.setText("<html>");
							lblNewLabel.setText(lblNewLabel.getText()+"<br/>");
							if(ob2.countTables>0) {
								Table t=ob2.dbtables.get(ob2.countTables-1);
								for(int i=0;i<t.getNamesCol().size();i++) {
									if(i==t.getNamesCol().size()-1) {
										lblNewLabel.setText(lblNewLabel.getText()+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+t.getNamesCol().get(i));
										lblNewLabel.setText(lblNewLabel.getText()+"<br/>");
										lblNewLabel.setText(lblNewLabel.getText()+"<br/>");
									}
									else {
										lblNewLabel.setText(lblNewLabel.getText()+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+t.getNamesCol().get(i));
									}
								}
							}
							for(int i=0;i<arr.length;i++) {
								for(int j=0;j<arr[0].length;j++) {
									if(j==arr[0].length-1) { 
										lblNewLabel.setText(lblNewLabel.getText()+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"|"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+arr[i][j].toString()+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"|");
										lblNewLabel.setText(lblNewLabel.getText()+"<br/>");
										lblNewLabel.setText(lblNewLabel.getText()+"<br/>");
									}
									else {
										lblNewLabel.setText(lblNewLabel.getText()+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"|"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+arr[i][j].toString());
									}
								}
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else if(function.equals("structure")) {
						try {
							boolean result=ob2.executeStructureQuery(query);
							if(result==true) {
								lblNewLabel.setText("Done");
							}
							else {
								lblNewLabel.setText("Failed");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
			}
		});
		btnNewButton.setBounds(375, 310, 163, 52);
		frame.getContentPane().add(btnNewButton);
		
		
		
		
		
		
	}
}
