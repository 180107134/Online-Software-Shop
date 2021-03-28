package SoftSale;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class EditClientDialog extends JDialog
{
	public static int TRANSACTION_ID;
	public static String PRODUCT_NAME;
	public static int PRODUCT_ID;
	public static int QUANTITY;
	public static String RATING;
	public static int TRANSACTION_DATE;
	public static String APPROVALSTATUS;
	
	private static final long serialVersionUID = 1;
	
	boolean _isExisted = false;
	
	database.StudentSearchCriteria_t _criteria = new database.StudentSearchCriteria_t();
	
	JTextField _ProductNameField = new JTextField();
	JTextField _QuantityField = new JTextField();
	JComboBox<String> _RatingBox = new JComboBox<String>();
	DatePicker _TransactionDatePicker = new DatePicker();
	JComboBox<String> _ApprovalStatusBox = new JComboBox<String>();	

	JButton okButton = new JButton("Save");
	
    public Vector<?> ratingVec = new Vector<Object>();
	
	database _studentDatabase = database.getInstance();
	
	EditClientDialog()
	{
		this.setTitle("Edit");
		
		this.setModal(true);
		this.setSize(240, 300);
		
		Container mainPane = this.getRootPane().getContentPane();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

		TRANSACTION_ID = MainWindow.TRANSACTION_ID;

		JPanel ProductNamePanel = new JPanel();
		ProductNamePanel.setBorder(BorderFactory.createTitledBorder("Product name"));
		ProductNamePanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		ProductNamePanel.setLayout(new BoxLayout(ProductNamePanel, BoxLayout.X_AXIS));
		_ProductNameField.setMaximumSize(new Dimension(240, 20));	
		_ProductNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));		
		ProductNamePanel.add(_ProductNameField);
		_ProductNameField.setText(MainWindow.PRODUCT_NAME);
		_ProductNameField.setEditable(false);
		PRODUCT_ID = _studentDatabase.getProductId(MainWindow.PRODUCT_NAME);
		
		JPanel QuantityPanel = new JPanel();
		QuantityPanel.setBorder(BorderFactory.createTitledBorder("Quantity"));
		QuantityPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		QuantityPanel.setLayout(new BoxLayout(QuantityPanel, BoxLayout.X_AXIS));
		_QuantityField.setMaximumSize(new Dimension(240, 20));
		_QuantityField.setFont(new Font("Tahoma", Font.PLAIN, 12));		
		QuantityPanel.add(_QuantityField);		
		_QuantityField.setText("" + MainWindow.QUANTITY);
		if (MainWindow.GLOBAL_MEMBER_ID > 0) {
			_QuantityField.setEnabled(true);
		}
		else {
			_QuantityField.setEnabled(false);
		}
		
		JPanel RatingPanel = new JPanel();
		RatingPanel.setBorder(BorderFactory.createTitledBorder("Rating"));
		RatingPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		RatingPanel.setLayout(new BoxLayout(RatingPanel, BoxLayout.X_AXIS));		
		_RatingBox.setMaximumSize(new Dimension(240, 20));
		_RatingBox.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		_RatingBox.addItem("");		
		ratingVec = _studentDatabase.getRatingList();
		for (int i = 0; i < ratingVec.size(); i++) {
			_RatingBox.addItem(ratingVec.elementAt(i).toString());
		}
		RatingPanel.add(_RatingBox);		
		_RatingBox.setSelectedItem(MainWindow.RATING);
		_RatingBox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				RATING = _RatingBox.getSelectedItem().toString();
	        }
	    });
		if (MainWindow.GLOBAL_MEMBER_ID > 0) {
			_RatingBox.setEnabled(true);
		}
		else {
			_RatingBox.setEnabled(false);
		}

		JPanel TransactionDatePanel = new JPanel();
		TransactionDatePanel.setBorder(BorderFactory.createTitledBorder("Order date"));
		TransactionDatePanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		TransactionDatePanel.setLayout(new BoxLayout(TransactionDatePanel, BoxLayout.X_AXIS));
		_TransactionDatePicker.setMaximumSize(new Dimension(240, 20));
		_TransactionDatePicker.setEnabled(false);
		TransactionDatePanel.add(_TransactionDatePicker);	
	    String sDate = MainWindow.TRANSACTION_DATE;  
	    Date date;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(sDate);
			_TransactionDatePicker.setDate(date);
		} catch (Exception e2) {
			e2.printStackTrace();
		} 

		JPanel ApprovalStatusPanel = new JPanel();
		ApprovalStatusPanel.setBorder(BorderFactory.createTitledBorder("Approval status"));
		ApprovalStatusPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		ApprovalStatusPanel.setLayout(new BoxLayout(ApprovalStatusPanel, BoxLayout.X_AXIS));
		_ApprovalStatusBox.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		_ApprovalStatusBox.addItem("APPROVED");
		_ApprovalStatusBox.addItem("REJECTED");
		_ApprovalStatusBox.addItem("PENDING");
		_ApprovalStatusBox.setMaximumSize(new Dimension(240, 20));
		ApprovalStatusPanel.add(_ApprovalStatusBox);		
		_ApprovalStatusBox.setSelectedItem(MainWindow.APPROVALSTATUS);
		if (MainWindow.GLOBAL_MEMBER_ID > 0) {
			_ApprovalStatusBox.setEnabled(false);
		}
		else {
			_ApprovalStatusBox.setEnabled(true);
		}
		_ApprovalStatusBox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				APPROVALSTATUS = _ApprovalStatusBox.getSelectedItem().toString();
	        }
	    });
		//Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		
		okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				try {
					
					QUANTITY = Integer.parseInt(_QuantityField.getText());
					if (_studentDatabase.updateClient(MainWindow.GLOBAL_MEMBER_ID, TRANSACTION_ID, PRODUCT_ID, QUANTITY, RATING, APPROVALSTATUS)) {
												
						JOptionPane.showMessageDialog(null,"Product successfully updated");
						if (checkData())
						{
							_isExisted = true;
							setVisible(false);
						}
						else
						{
							_isExisted = false;
						}

					}
					else {
						JOptionPane.showMessageDialog(null,"Can not update!");

					}
					setVisible(false);	
				} catch (Exception e1) {
					
					e1.printStackTrace();
					setVisible(false);	
				}
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
		
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		mainPane.add(ProductNamePanel);
		mainPane.add(QuantityPanel);
		mainPane.add(RatingPanel);
		mainPane.add(TransactionDatePanel);
		mainPane.add(ApprovalStatusPanel);
		mainPane.add(buttonPanel);	

		this.pack();
		
		this.setResizable(false);
		this.setSize(240, 300);		

		}
	
	boolean checkData()
	{
		_criteria.reset();
		
		_criteria.MEMBER_ID = MainWindow.GLOBAL_MEMBER_ID;
		_criteria.PRODUCT_NAME = MainWindow.GLOBAL_PRODUCT_NAME;
		_criteria.START_DATE = MainWindow.GLOBAL_START_DATE;
		_criteria.END_DATE = MainWindow.GLOBAL_END_DATE;
		_criteria.APPROVALSTATUS = MainWindow.GLOBAL_APPROVALSTATUS;			

	return true;
	}
	
	public boolean isExisted()
	{
		return _isExisted;
	}
	
	public database.StudentSearchCriteria_t criteria()
	{
		return _criteria;
	}

	
	@Override
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			_isExisted = false;
		}
		
		super.setVisible(visible);
	}
	
	public boolean isDigit(String txt) {
		boolean _isDigit = false;
		try {
			Integer.parseInt(txt);
			_isDigit = true;
		}catch (Exception e) {
			e.printStackTrace();
			_isDigit = false;
		}
		return _isDigit;
	}
}

