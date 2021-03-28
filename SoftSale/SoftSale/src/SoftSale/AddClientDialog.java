package SoftSale;


import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionEvent;


public class AddClientDialog extends JDialog
{
	int MEMBER_ID = 0;
	int PRODUCT_ID = 0;
	int QUANTITY = 0;
	String APPROVALSTATUS = "";
	String TRANSACTION_DATE = "";	
	    
	private static final long serialVersionUID = 1;
	
	boolean _isExisted = false;

	JComboBox<String> _ProductNameBox = new JComboBox<String>();	
	JTextField _QuantityField = new JTextField();
	
    public Vector<?> productnameVec = new Vector<Object>();
    
	AddClientDialog()
	{
		try {
		this.setTitle("Add new order");
		
		this.setModal(true);
		this.setSize(300, 150);
		
		database _studentDatabase = database.getInstance();
		
		Container mainPane = this.getRootPane().getContentPane();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		
		JPanel ProductNamePanel = new JPanel();
		ProductNamePanel.setBorder(BorderFactory.createTitledBorder("Select Product"));
		ProductNamePanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		ProductNamePanel.setLayout(new BoxLayout(ProductNamePanel, BoxLayout.X_AXIS));
		_ProductNameBox.setPreferredSize(new Dimension(290, 18));
		ProductNamePanel.add(_ProductNameBox);
		_ProductNameBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		productnameVec = _studentDatabase.getProductNameList();
		for (int i = 0; i < productnameVec.size(); i++) {
			_ProductNameBox.addItem(productnameVec.elementAt(i).toString());
		}

		JPanel QuantityPanel = new JPanel();
		QuantityPanel.setBorder(BorderFactory.createTitledBorder("Quantity"));
		QuantityPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		QuantityPanel.setLayout(new BoxLayout(QuantityPanel, BoxLayout.X_AXIS));
		
		_QuantityField.setMaximumSize(new Dimension(290, 20));
		QuantityPanel.add(_QuantityField);		
		_QuantityField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_QuantityField.setText("0");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createTitledBorder(""));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent e)
			{
				MEMBER_ID = MainWindow.GLOBAL_MEMBER_ID;
				
				PRODUCT_ID = _studentDatabase.getProductId(_ProductNameBox.getSelectedItem().toString());

				QUANTITY = Integer.parseInt(_QuantityField.getText());
				
				APPROVALSTATUS = "PENDING";
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 0);
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				TRANSACTION_DATE = format.format(cal.getTime());
				System.out.println("TRANSACTION_DATE = " + TRANSACTION_DATE);
				if (_studentDatabase.addOrder(MEMBER_ID, PRODUCT_ID, QUANTITY, APPROVALSTATUS, TRANSACTION_DATE)) {
					
					JOptionPane.showMessageDialog(null,"New order added to the database");
					
				}
				setVisible(false);	
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_isExisted = false;
				setVisible(false);
			}
		});
		
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
			
		mainPane.add(ProductNamePanel);
		mainPane.add(QuantityPanel);
		mainPane.add(buttonPanel);
		
		this.pack();
		
		this.setResizable(false);
	} catch(Exception e) {
		e.printStackTrace();
		}

	}
	public boolean isExisted()
	{
		return _isExisted;
	}
	
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			_isExisted = false;
		}
		
		super.setVisible(visible);
	}

}
