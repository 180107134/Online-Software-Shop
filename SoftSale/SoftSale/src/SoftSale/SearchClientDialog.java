package SoftSale;

import javax.swing.JDialog;
import javax.swing.JPanel;
import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Vector;

public class SearchClientDialog extends JDialog implements ItemListener
{
	private static final long serialVersionUID = 1;
	
	boolean _isExisted = false;

	database.StudentSearchCriteria_t _criteria = new database.StudentSearchCriteria_t();
	
	JCheckBox _ProductNameCheck = new JCheckBox();
	JCheckBox _StartDateCheck = new JCheckBox();
	JCheckBox _EndDateCheck = new JCheckBox();
	JCheckBox _ApprovalStatusCheck = new JCheckBox();
	
	JComboBox<String> _ProductNameBox = new JComboBox<String>();	
	DatePicker _StartPicker = new DatePicker();
	DatePicker _EndPicker = new DatePicker();
	JComboBox<String> _ApprovalStatusBox = new JComboBox<String>();	
		
    public Vector<?> productnameVec = new Vector<Object>();
    
    public static String PRODUCT_NAME = "";
    public static String START_DATE = "";	
    public static String END_DATE = "";
    public static String APPROVALSTATUS = "";	
	
	database _studentDatabase = database.getInstance();
	
	SearchClientDialog()
	{	
		
		MainWindow.GLOBAL_PRODUCT_NAME = "";
		MainWindow.GLOBAL_START_DATE = "";
		MainWindow.GLOBAL_END_DATE = "";
		MainWindow.GLOBAL_APPROVALSTATUS = "";
		
		this.setTitle("Search");
		this.setModal(true);
		this.setSize(300, 270);
		
		Container mainPane = this.getRootPane().getContentPane();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

		//Product name
		JPanel ProductNamePanel = new JPanel();
		ProductNamePanel.setBorder(BorderFactory.createTitledBorder("Select product"));
		ProductNamePanel.setLayout(new BoxLayout(ProductNamePanel, BoxLayout.X_AXIS));		
		ProductNamePanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		_ProductNameBox.addItem("");
		ProductNamePanel.add(_ProductNameCheck);
		ProductNamePanel.add(_ProductNameBox);
		_ProductNameBox.setMaximumSize(new Dimension(270,20));
		_ProductNameBox.setFont(new Font("Tahoma", Font.PLAIN, 12));				

		//Start date
		JPanel StartDatePanel = new JPanel();
		StartDatePanel.setBorder(BorderFactory.createTitledBorder("Select start date"));
		StartDatePanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		StartDatePanel.setLayout(new BoxLayout(StartDatePanel, BoxLayout.X_AXIS));		
		Calendar beginDate = Calendar.getInstance();
		beginDate.add(Calendar.DAY_OF_MONTH, -1);	
		try {
			_StartPicker.setDate(beginDate.getTime());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		StartDatePanel.add(_StartDateCheck);
		StartDatePanel.add(_StartPicker);
		_StartPicker.setMaximumSize(new Dimension(270, 20));	

		//End date
		JPanel EndDatePanel = new JPanel();
		EndDatePanel.setBorder(BorderFactory.createTitledBorder("Select end date"));
		EndDatePanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		EndDatePanel.setLayout(new BoxLayout(EndDatePanel, BoxLayout.X_AXIS));		
		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.DAY_OF_MONTH, 0);		
		try {
			_EndPicker.setDate(endDate.getTime());
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		EndDatePanel.add(_EndDateCheck);
		EndDatePanel.add(_EndPicker);
		_EndPicker.setMaximumSize(new Dimension(270,20));				

		//Semester
		JPanel ApprovalStatusPanel = new JPanel();
		ApprovalStatusPanel.setBorder(BorderFactory.createTitledBorder("Select status"));
		ApprovalStatusPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		ApprovalStatusPanel.setLayout(new BoxLayout(ApprovalStatusPanel, BoxLayout.X_AXIS));				
		_ApprovalStatusBox.addItem("");
		ApprovalStatusPanel.add(_ApprovalStatusCheck);
		ApprovalStatusPanel.add(_ApprovalStatusBox);
		_ApprovalStatusBox.setMaximumSize(new Dimension(270,20));				
		_ApprovalStatusBox.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		
		productnameVec = _studentDatabase.getProductNameList();
		for (int i = 0; i < productnameVec.size(); i++) {
			_ProductNameBox.addItem(productnameVec.elementAt(i).toString());
		}
		_ProductNameBox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				PRODUCT_NAME = _ProductNameBox.getSelectedItem().toString();
	        }
	    });		
		_ApprovalStatusBox.addItem("APPROVED");
		_ApprovalStatusBox.addItem("REJECTED");
		_ApprovalStatusBox.addItem("PENDING");
		_ApprovalStatusBox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				APPROVALSTATUS = _ApprovalStatusBox.getSelectedItem().toString();
	        }
	    });

		//Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Press Ok or Cancel"));
		buttonPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));				
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
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
		mainPane.add(StartDatePanel);
		mainPane.add(EndDatePanel);
		mainPane.add(ApprovalStatusPanel);
		mainPane.add(buttonPanel);

		_ProductNameCheck.addItemListener(this);
		_StartDateCheck.addItemListener(this);
		_EndDateCheck.addItemListener(this);
		_ApprovalStatusCheck.addItemListener(this);
				
		_ProductNameBox.setEnabled(false);
		_StartPicker.setEnabled(false);
		_EndPicker.setEnabled(false);
		_ApprovalStatusBox.setEnabled(false);

		this.pack();
		
		this.setResizable(false);
		this.setSize(300, 270);	
		}
	
	boolean checkData()
	{
		_criteria.reset();
		
		_criteria.MEMBER_ID = MainWindow.GLOBAL_MEMBER_ID;			
		
		if (_ProductNameCheck.getModel().isSelected()) {
			
			_criteria.PRODUCT_NAME = PRODUCT_NAME;
			MainWindow.GLOBAL_PRODUCT_NAME = PRODUCT_NAME;
		}		
		
		if (_StartDateCheck.getModel().isSelected()) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(_StartPicker.getDate());
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			
			String START_DATE = format1.format(cal.getTime());

			_criteria.START_DATE = START_DATE;
			MainWindow.GLOBAL_START_DATE = START_DATE;
		}

		if (_EndDateCheck.getModel().isSelected()) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(_EndPicker.getDate());
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			
			String END_DATE = format1.format(cal.getTime());

			_criteria.END_DATE = END_DATE;
			MainWindow.GLOBAL_END_DATE = END_DATE;

		}
		
		if (_ApprovalStatusCheck.getModel().isSelected()) {

			_criteria.APPROVALSTATUS = APPROVALSTATUS;
			MainWindow.GLOBAL_APPROVALSTATUS = APPROVALSTATUS;

		}
		
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
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getItemSelectable();
		
		boolean itemSelected = (e.getStateChange() == ItemEvent.SELECTED);
		if (source == _ProductNameCheck)
		{
			_ProductNameBox.setEnabled(itemSelected);
		}		
		else if (source == _StartDateCheck)
		{
			_StartPicker.setEnabled(itemSelected);
		}
		else if (source == _EndDateCheck)
		{
			_EndPicker.setEnabled(itemSelected);
		}
		else if (source == _ApprovalStatusCheck)
		{
			_ApprovalStatusBox.setEnabled(itemSelected);
		}
		
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
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			_isExisted = false;
		}
		
		super.setVisible(visible);
	}

}

